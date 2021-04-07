package com.example.myapplication.weight.drawlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class DrawContent extends LinearLayout {
    public DrawContent(Context context) {
        super(context);
    }

    public DrawContent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawContent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollY(float y, float percent) {
        float height = getHeight();
                  int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View view = getChildAt(i);

            float centerY = view.getTop() + view.getHeight() / 2;
            float transX = 0;
            if (y - centerY >= 0) {
                transX = 300f-(y - centerY) / y * 300f;
            } else {
                transX = 300f-(centerY-y) /( height-y) * 300f;
            }
            view.setTranslationX(transX*percent);
        }
    }

}
