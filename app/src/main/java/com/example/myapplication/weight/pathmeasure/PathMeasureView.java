package com.example.myapplication.weight.pathmeasure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class PathMeasureView extends View {
    Paint mPaint, mPaintBitmap;
    Path path;
    PathMeasure pathMeasure;
    PointF basePoint;
    float r;
    Bitmap mBitmap;
    Matrix matrix;

    public PathMeasureView(Context context) {
        super(context);
        init();
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        pos = new float[2];
        tan = new float[2];

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1);

        mPaintBitmap = new Paint();
        mPaintBitmap.setAntiAlias(true);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 12;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow, options);

        matrix = new Matrix();

        path = new Path();

    }

    float[] pos, tan;
    float sumLength = 0;
    float currentValue = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(basePoint.x, basePoint.y);

        path.reset();
        path.addCircle(0, 0, r, Path.Direction.CW);
        pathMeasure = new PathMeasure(path, true);
        sumLength = pathMeasure.getLength();

        canvas.drawPath(path, mPaint);

        currentValue += 0.01;
        if (currentValue >= 1)
            currentValue = 0;
        canvas.save();
        pathMeasure.getPosTan(currentValue * sumLength, pos, tan);
        matrix.reset();
        float degress = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);

        /**
         * post set pre 去区别
         * set 每一次设置修改绘制坐标轴，回去清空之前的设置
         *          如setRotate 再setTranslate 那么setRotate会无效，因为再设置setTranslate的时候，清空之前的设置
         * post 可以保存上一次的，也就是说 先postTranslate，再postRotate
         *          那么第一次的平移修改了坐标轴和绘制中心，那么后面的旋转是在修改了坐标轴的基础上旋转的
         * pre 可以理解为提前 先postTranslate，再preRotate == 先postRotate 再postTranslate
         */
        matrix.postRotate(degress, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        matrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, matrix, mPaintBitmap);
        canvas.restore();
        postInvalidateDelayed(100);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        basePoint = new PointF();
        basePoint.x = w / 2;
        basePoint.y = h / 2;
        r = (Math.min(w, h) - 50) / 2;
    }
}
