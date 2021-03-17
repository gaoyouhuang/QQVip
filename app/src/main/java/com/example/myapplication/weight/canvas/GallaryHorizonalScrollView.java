package com.example.myapplication.weight.canvas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GallaryHorizonalScrollView extends HorizontalScrollView implements View.OnTouchListener {

    LinearLayout root;

    public GallaryHorizonalScrollView(Context context) {
        super(context);
        init();
    }

    public GallaryHorizonalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GallaryHorizonalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        //在ScrollView里面放置一个水平线性布局，再往里面放置很多ImageView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        root = new LinearLayout(getContext());
        root.setLayoutParams(params);
        root.setOrientation(LinearLayout.HORIZONTAL);
        setOnTouchListener(this);
    }

    int imgWidth;
    int centerX;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View child = root.getChildAt(0);
        imgWidth = child.getWidth();
        centerX = getWidth() / 2;
        root.setPadding(centerX - (imgWidth / 2), 0, centerX - (imgWidth / 2), 0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                move();
                break;
        }
        return false;
    }

    public void addView(Drawable[] drawable) {
        for (int i = 0; i < drawable.length; i++) {
            ImageView view = new ImageView(getContext());
            view.setImageDrawable(drawable[i]);
            if (i == 0) {
                view.setImageLevel(5000);
            }
            root.addView(view);
        }
        addView(root);
    }

    private void move() {
        int scrollX = getScrollX();
        System.out.println(scrollX + " " + imgWidth);
        float scale = (float)imgWidth / 5000L;
        //当前的下边和下一章下标,因为可能会截图两张部分图片
        int nowIndex = (int) (scrollX / imgWidth);
        int nextIndex = nowIndex + 1;
        //图片有效移动  13
        int useful = (int) (scrollX % imgWidth);
        //算出彩色 level
        int level = (int) (5000 - useful / scale);
        level = level < 0 ? 0 : level;
        if (nextIndex >= root.getChildCount()) {
            //说明nowIndex已经是最后一个
            ImageView childAt = (ImageView) root.getChildAt(nowIndex);
            childAt.setImageLevel(5000);
        } else {
            //left
            ImageView leftChild = (ImageView) root.getChildAt(nowIndex);
            leftChild.setImageLevel(level);
            if (level != 5000) {
                //right
                ImageView rightChild = (ImageView) root.getChildAt(nextIndex);
                rightChild.setImageLevel(10000-level);
            }
        }
    }
}
