package com.example.myapplication.weight.dispatchtouch;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.weight.dispatchtouch.tools.ItemTouchHelper;


public class MyCallBack extends ItemTouchHelper.Callback {
    Adapter adapter;

    public MyCallBack(Adapter adapter) {
        this.adapter = adapter;
    }

    /**
     * 表示ItemTouchHelper拦截手势的方向
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
    }

    /**
     * @param recyclerView
     * @param viewHolder   被拖拽的item
     * @param target       被重合的item
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        adapter.onMove(viewHolder, target);
        return true;
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//        adapter.onDele(viewHolder);
    }

    //滑动消失的距离，当滑动小于这个值的时候会删除这个item，否则不会视为删除
    //返回值作为用户视为拖动的距离
    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return 0.4f;
    }

    //返回值滑动消失的距离，滑动小于这个值不消失，大于消失
    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 5f;
    }

    //设置手指离开后ViewHolder的动画时间
    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return 100;
    }

    /**
     * 在进行拖动和侧滑的时候 回去回调
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dY != 0 && dX == 0)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        System.out.println("dx = " + dX + " dy = " + dY);
        Adapter.ItemHolder holder = (Adapter.ItemHolder) viewHolder;
        if (Math.abs(dX) < holder.llLeft.getWidth())
            holder.tv.setTranslationX(dX);
        else{
            holder.tv.setTranslationX(-holder.llLeft.getWidth());
        }
    }
}
