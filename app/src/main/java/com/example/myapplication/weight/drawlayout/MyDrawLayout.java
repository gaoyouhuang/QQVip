package com.example.myapplication.weight.drawlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MyDrawLayout extends DrawerLayout implements DrawerLayout.DrawerListener {
    public MyDrawLayout(@NonNull Context context) {
        super(context);
    }

    public MyDrawLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDrawLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addDrawerListener(this);
    }

    DrawControl drawControl = null;
    float slideOffset = 0;

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (child.getClass().getSimpleName().contains("DrawContent")) {
            child.setLayoutParams(params);
            drawControl = new DrawControl(getContext(), (DrawContent) child);
            drawControl.setLayoutParams(params);
            addView(drawControl);
        } else
            super.addView(child, params);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        drawControl.setScrollY(ev.getY(),slideOffset);
        return super.onTouchEvent(ev);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        this.slideOffset = slideOffset;
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
