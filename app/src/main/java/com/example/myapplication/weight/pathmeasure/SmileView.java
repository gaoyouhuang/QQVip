package com.example.myapplication.weight.pathmeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;


public class SmileView extends View {
    Path mPathOut, mPathLeye, mPathReye, mPathmouse;
    Paint mPaintEye, mPaintLine, mPaintNull;

    int mWidth, mHeight;

    PathMeasure outMeasure;

    Matrix matrix;

    float dy =0;
    public SmileView(Context context) {
        super(context);
        init();
    }

    public SmileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        mPaintNull = new Paint();
        mPaintNull.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintNull.setColor(Color.WHITE);
        mPaintNull.setStrokeWidth(10);
        mPaintNull.setStrokeCap(Paint.Cap.ROUND);

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setStrokeWidth(10);
        mPaintLine.setColor(Color.BLACK);
        //TODO 设置险段两头是半圆形
        mPaintLine.setStrokeCap(Paint.Cap.ROUND);
        //TODO 设置lineto的连接处是圆形的
        mPaintLine.setStrokeJoin(Paint.Join.ROUND);

        mPaintEye = new Paint();
        mPaintEye.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintEye.setColor(Color.BLACK);

        mPathLeye = new Path();
        mPathReye = new Path();
        mPathmouse = new Path();
        mPathOut = new Path();

        matrix = new Matrix();

        startAnimation();
    }

    ValueAnimator valueAnimator;

    public void startAnimation() {
        valueAnimator = ValueAnimator.ofFloat(0f, 100f, 0f);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dy = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        //out
        /**
         * addRoundRect
         * 绘制矩形，可以设置四个角的角度
         */
//        mPathOut.addRoundRect(new RectF(40, 40, mWidth, mHeight), mWidth / 8, mHeight / 8, Path.Direction.CW);
        mPathOut.addRect(new RectF(40, 40, mWidth, mHeight), Path.Direction.CW);
        canvas.drawPath(mPathOut, mPaintLine);

        //头上空白处
        outMeasure = new PathMeasure(mPathOut, false);
        Path segmentPath = new Path();
        boolean flag = outMeasure.getSegment(mWidth / 3, mWidth * 2 / 3, segmentPath, true);
        canvas.drawPath(segmentPath, mPaintNull);

        canvas.save();
        //eye
        canvas.drawCircle(mWidth / 3, mHeight / 4+dy, 100, mPaintEye);
        canvas.drawCircle(mWidth * 2 / 3, mHeight / 4+dy, 100, mPaintEye);
        //mouse
        mPathmouse.reset();
        mPathmouse.moveTo(mWidth / 3, mHeight * 3 / 4+dy);
        mPathmouse.quadTo(mWidth / 2, mHeight / 2+dy, mWidth * 2 / 3, mHeight * 3 / 4+dy);
        canvas.drawPath(mPathmouse, mPaintLine);
        canvas.restore();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHeight = h - 80;
        mWidth = w - 80;

    }
}
