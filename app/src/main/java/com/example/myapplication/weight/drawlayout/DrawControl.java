package com.example.myapplication.weight.drawlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DrawControl extends RelativeLayout {
    public DrawControl(@NonNull Context context, DrawContent drawContent) {
        super(context);
        this.drawContent = drawContent;
        init();
    }

    public DrawControl(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    DrawBg drawBg;
    DrawContent drawContent;

    private void init() {
        drawBg = new DrawBg(getContext());
        drawBg.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(drawBg);
        addView(drawContent);
    }

    public void setScrollY(float y, float percent) {
        drawBg.setScrollY(y, percent);
        drawContent.setScrollY(y, percent);
    }
}
