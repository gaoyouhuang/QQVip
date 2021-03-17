package com.example.myapplication.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
//TODO 雷达
public class RadarView extends View {
    public static final String TAG = "RadarView";

    public RadarView(Context context) {
        super(context);
        init();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //园的画笔和雷达的画笔
    Paint mPaintCircle, mPaintRader;
    //当前雷达度数
    int nowDegrees = 0;
    //雷达素的
    int speed = 5;
    //渐变 梯度渲染
    SweepGradient sweepGradient;
    //半径
    int radius;
    //宽
    int width;
    //高
    int height;

    Matrix matrix = new Matrix();

    float[] radiusList = new float[]{0.1f, 0.3f, 0.5f, 0.7f, 0.9f};

    public void init() {
        mPaintCircle = new Paint();
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStrokeWidth(1);
        mPaintCircle.setColor(Color.parseColor("#B0C4DE"));

        mPaintRader = new Paint();
        mPaintRader.setAntiAlias(true);
        mPaintRader.setStyle(Paint.Style.FILL_AND_STROKE);

        post(new Runnable() {
            @Override
            public void run() {
                nowDegrees = (nowDegrees + speed) % 360;
                matrix.setRotate(nowDegrees, width / 2, height / 2);
                invalidate();
                postDelayed(this, 50);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure");
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        radius = Math.min(height, width) / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged");
        sweepGradient = new SweepGradient(width / 2, height / 2, new int[]{Color.TRANSPARENT, Color.parseColor("#84B5CA")}, null);
        mPaintRader.setShader(sweepGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
        for (float r : radiusList) {
            canvas.drawCircle(width / 2, height / 2, radius * r, mPaintCircle);
        }

        canvas.save();
        canvas.concat(matrix);
        canvas.drawCircle(width / 2, height / 2, radius, mPaintRader);
        canvas.restore();
//        sweepGradient.setLocalMatrix(matrix);
//        canvas.drawCircle(width/2,height/2,radius,mPaintRader);


    }
}


