package com.example.myapplication.weight.canvas;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RevealDrawable extends Drawable {

    Drawable unSelectDrawable, selectDrawable;

    public RevealDrawable(Drawable unSelectDrawable, Drawable selectDrawable) {
        this.unSelectDrawable = unSelectDrawable;
        this.selectDrawable = selectDrawable;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        /**
         * level表示彩色的占比，5000：width
         * 0表示全灰
         * 5000表示全彩色
         */
        int level = getLevel();
        Rect bounds = getBounds();
        float scale = (float)getIntrinsicWidth() / 5000L;

        if (level == 0) {
            //灰色
            unSelectDrawable.draw(canvas);
        } else if (level == 5000) {
            selectDrawable.draw(canvas);
        } else {
            if (level < 5000) {
                int seleWitdh = (int) (level * scale);
                canvas.save();
                Rect temp = new Rect();
                Gravity.apply(Gravity.RIGHT, seleWitdh, getIntrinsicHeight(), bounds, temp);
                canvas.clipRect(temp);
                selectDrawable.draw(canvas);
                canvas.restore();

                int unSeleWidth = getIntrinsicWidth() - seleWitdh;
                canvas.save();
                Gravity.apply(Gravity.LEFT, unSeleWidth, getIntrinsicHeight(), bounds, temp);
                canvas.clipRect(temp);
                unSelectDrawable.draw(canvas);
                canvas.restore();
            } else {
                level = level - 5000;
                int seleWitdh = (int) (level * scale);
                canvas.save();
                Rect temp = new Rect();
                Gravity.apply(Gravity.LEFT, seleWitdh, getIntrinsicHeight(), bounds, temp);
                canvas.clipRect(temp);
                selectDrawable.draw(canvas);
                canvas.restore();

                int unSeleWidth = getIntrinsicWidth() - seleWitdh;
                canvas.save();
                Gravity.apply(Gravity.RIGHT, unSeleWidth, getIntrinsicHeight(), bounds, temp);
                canvas.clipRect(temp);
                unSelectDrawable.draw(canvas);
                canvas.restore();
            }
        }
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return super.onLevelChange(level);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }


    @Override
    public int getIntrinsicWidth() {
        //得到Drawable的实际宽度
        return Math.max(unSelectDrawable.getIntrinsicWidth(),
                selectDrawable.getIntrinsicWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        //得到Drawable的实际高度
        return Math.max(unSelectDrawable.getIntrinsicHeight(),
                selectDrawable.getIntrinsicHeight());
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        unSelectDrawable.setBounds(bounds);
        selectDrawable.setBounds(bounds);
        super.onBoundsChange(bounds);
    }

    @SuppressLint("WrongConstant")
    @Override
    public int getOpacity() {
        return 0;
    }
}
