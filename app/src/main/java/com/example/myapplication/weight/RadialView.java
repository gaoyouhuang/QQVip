package com.example.myapplication.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
//TODO 水波纹
public class RadialView extends View {

    RadialGradient radialGradient;
    Paint radiaPaint;

    int raduis;
    int width, height;

    int flag = 1;
    public RadialView(Context context) {
        super(context);
        init();

    }

    public RadialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public RadialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public void init() {
        radiaPaint = new Paint();
        radiaPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        radiaPaint.setAntiAlias(true);
        post(new Runnable() {
            @Override
            public void run() {
                flag+=5;
                if (flag>=raduis)
                    flag = 1;
                invalidate();
                postDelayed(this,50);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        raduis = Math.min(height, width);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        radialGradient = new RadialGradient(width / 2, height / 2,  flag,
                new int[] { Color.WHITE, Color.TRANSPARENT },null, Shader.TileMode.CLAMP);
        radiaPaint.setShader(radialGradient);

        canvas.drawCircle(width / 2, height / 2, raduis, radiaPaint);
    }
}
