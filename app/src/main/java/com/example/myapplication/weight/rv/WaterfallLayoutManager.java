package com.example.myapplication.weight.rv;

import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WaterfallLayoutManager extends RecyclerView.LayoutManager {
    int recycleHeight = 0;
    SparseArray<Rect> rectList;
    //整个控件偏移量
    int allScrollY = 0;
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        System.out.println("111111111111111111");
        if (getItemCount() <= 0)
            return;
        //TODO isPerlayout false  表示不在预动画 相对于就是测量好了 可以获取宽高
        if (state.isPreLayout())
            return;
//        TODO 首先整所有的item实际上已经都经历了measure 但是为了要实现瀑布流 我们需要从新打乱所有的item的布局

        /**
         * 将视图分离放入scrap缓存中，以准备重新对view进行排版
         * 那么此时geiChildCount = 0
         */
        detachAndScrapAttachedViews(recycler);
        rectList = new SparseArray<>();
        int[] tops = new int[]{0, 0, 0};
        int left = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
//            TODO 从新将view添加到视图中 还可以通过测量去获取view的宽高
            addView(view);
//            TODO 由于是可滑动 约束设置为UNSPECIFIED即可
            measureChildWithMargins(view, View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//            TODO 获取view的宽高
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);
//            TODO left： 0  width  2*width
//            TODO top：tops[0] tops[1] tops[2]
            left = i % 3 * width;
            Rect rect = new Rect(left, tops[i % 3], left + width, tops[i % 3] + height);
            tops[i % 3] = tops[i % 3] + height;

            rectList.put(i, rect);
        }
        recycleHeight = Math.max(Math.max(tops[0], tops[1]), tops[2]);
//TODO 获取内容总宽高
        fillShowItem(recycler, state);
    }

    /**
     * 显示可见item
     *
     * @param recycler
     * @param state
     */
    private void fillShowItem(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        Rect showRect = new Rect(0, allScrollY, getWidth(), allScrollY + getHeight());

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            Rect child = rectList.get(i);
            if (!Rect.intersects(showRect, child)) {
                System.out.println("remove child " + i);
                removeAndRecycleView(childView, recycler);
            }
        }
        // TODO 可见区域的矩阵
        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(showRect, rectList.get(i))) {
                View view = recycler.getViewForPosition(i);
                addView(view);
                measureChildWithMargins(view, View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                Rect viewRect = rectList.get(i);
                layoutDecorated(view, viewRect.left, viewRect.top - allScrollY, viewRect.right, viewRect.bottom - allScrollY);
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        TODO 上滑 dy>0
        int scrollY = dy;
//        TODO 表示已经滑到顶部 不可以再向下滑动
        if (scrollY < 0) {
            if (allScrollY + scrollY <= 0)
                scrollY = -allScrollY;
        } else {
            //TODO 表示已经滑倒底部 不能再往上滑动
            if (allScrollY + getHeight() + scrollY >= recycleHeight) {
                scrollY = recycleHeight - allScrollY - getHeight();
            }
        }
        allScrollY += scrollY;
        System.out.println("scrollY " + scrollY+" allScrollY "+allScrollY);
        offsetChildrenVertical(scrollY);
        fillShowItem(recycler, state);
        return scrollY;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }
}
