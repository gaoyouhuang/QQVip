package com.example.myapplication.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO 放大镜
public class MagnifierView extends View {
    Matrix matrix;
    Bitmap mBitmapBack, mBitmapMagnifier;
    //放大倍数
    private static final int FACTOR = 2;
    //放大镜的半径
    private static final int RADIUS = 100;
    ShapeDrawable shapeDrawable;
    Paint paint;
    public MagnifierView(Context context) {
        super(context);
        init();
    }

    public MagnifierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MagnifierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        //未放大的背景
        mBitmapBack = BitmapFactory.decodeResource(getResources(), R.mipmap.book_bg);
        //放大的背景 去要通过运算从中获取
        mBitmapMagnifier = mBitmapBack;
        mBitmapMagnifier = Bitmap.createScaledBitmap(mBitmapMagnifier, mBitmapBack.getWidth() * FACTOR, mBitmapBack.getHeight() * FACTOR, true);
        //放大镜的矩阵
        Rect rect = new Rect(0, 0, RADIUS * 2, RADIUS * 2);
        BitmapShader bitmapShader = new BitmapShader(mBitmapMagnifier, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //创建一个圆形的图片
        shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(bitmapShader);
        shapeDrawable.setBounds(rect);

        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //现实背景
        canvas.drawBitmap(mBitmapBack,0,0,paint);
        //现实放大镜
        shapeDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        //平移
        matrix.setTranslate(-x*2+50,-y*2+50);
        shapeDrawable.getPaint().getShader().setLocalMatrix(matrix);
        shapeDrawable.setBounds(new Rect((x-RADIUS),(y-RADIUS),(x+RADIUS),(y+RADIUS)));
        invalidate();
        return true;
    }
}
