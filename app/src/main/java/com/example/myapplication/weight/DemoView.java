package com.example.myapplication.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class DemoView extends View {
    public DemoView(Context context) {
        super(context);
        init();
    }

    public DemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint paint = new Paint();
    Matrix matrix = new Matrix();
    Bitmap backBitmap, bigBitmap;
    //放大倍数
    private static final int FACTOR = 2;
    //放大镜的半径
    private static final int RADIUS = 100;
    ShapeDrawable shapeDrawable;

    private void init() {
        paint.setAntiAlias(true);

        backBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.book_bg);

        bigBitmap = Bitmap.createScaledBitmap(backBitmap, backBitmap.getWidth() * FACTOR,
                backBitmap.getHeight() * FACTOR, true);
        BitmapShader bitmapShader = new BitmapShader(bigBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(bitmapShader);
        shapeDrawable.setBounds(new Rect(0, 0, RADIUS * 2, RADIUS * 2));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backBitmap, 0, 0, paint);
        shapeDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        matrix.setTranslate(-x * 2+RADIUS, -y * 2+RADIUS);
        shapeDrawable.getPaint().getShader().setLocalMatrix(matrix);
        shapeDrawable.setBounds(new Rect((x - RADIUS), (y - RADIUS), (x + RADIUS), (y + RADIUS)));
        invalidate();

        return true;
    }
}
