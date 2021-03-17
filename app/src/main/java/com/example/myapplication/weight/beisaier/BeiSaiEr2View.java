package com.example.myapplication.weight.beisaier;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.Arrays;


public class BeiSaiEr2View extends View {
    public BeiSaiEr2View(Context context) {
        super(context);
        init();
    }

    public BeiSaiEr2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BeiSaiEr2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Path path;
    Paint paint;
    int moveX;
    int movey;
    public void init() {
        path = new Path();

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //要reset 不然不是一根线，因为会保留之前的画线
//        path.reset();
        //moveto quadto需要比drawpath早，不然path没数据 还花什么
//        path.moveTo(100,500);
//        path.quadTo(moveX,movey,500,500);

        path.reset();
        // 画上半弧
        path.moveTo(200,  200);
        path.quadTo(300, 250, 400, 100);
        // 画下半弧
        path.lineTo(400, 400);
        path.quadTo(300, 250, 200, 300);
        //闭合PATH
        path.close() ;
        canvas.drawPath(path,paint);
        Paint paint_B = new Paint();
        paint_B.setColor(Color.BLACK);
        paint_B.setStrokeWidth(2);

        canvas.drawCircle(200,200,5,paint_B);
        canvas.drawCircle(300,250,5,paint_B);
        canvas.drawCircle(400,110,5,paint_B);
        canvas.drawCircle(400,400,5,paint_B);
        canvas.drawCircle(200,300,5,paint_B);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                movey = (int) event.getY();
                break;
        }
        invalidate();
        return true;
    }
}
