package com.example.myapplication.weight.drawlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawBg extends View {

    Path path;
    Paint paint;
    private float width;
    private float height;
    float ratio = 0.2f;

    public DrawBg(Context context) {
        super(context);
        init();
    }

    public DrawBg(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawBg(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        path = new Path();
    }

    public void setScrollY(float y,float percent) {
        path.reset();
        width = getWidth()*percent;
        height = getHeight();
        float startTop = -(height * ratio);
        float endTop = height + height * ratio;

        float controllX = width*3/2;

        path.moveTo(0, startTop);
        path.quadTo(controllX,y,0,endTop);

        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);

    }

}
