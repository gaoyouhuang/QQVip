package com.example.myapplication.weight.xformode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class EraserView extends View {
    public EraserView(Context context) {
        super(context);
        init();
    }

    public EraserView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EraserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint paintEraser;
    Bitmap mBitmapSrc, mBitmapDst;
    Path path;
    float mPreX,mPreY;
    public void init() {
        //TODO 关闭硬加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        paintEraser = new Paint();
        paintEraser.setStyle(Paint.Style.STROKE);
        paintEraser.setColor(Color.parseColor("#FF00FF"));
        paintEraser.setStrokeWidth(50);

        mBitmapSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.guaguaka);
        mBitmapDst = Bitmap.createBitmap(mBitmapSrc.getWidth(), mBitmapSrc.getHeight(), Bitmap.Config.ARGB_8888);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerId = canvas.saveLayer(0, 0, mBitmapSrc.getWidth(), mBitmapSrc.getHeight(), null, Canvas.ALL_SAVE_FLAG);

        Canvas c = new Canvas(mBitmapDst);
        c.drawPath(path, paintEraser);
        canvas.drawBitmap(mBitmapDst, 0, 0, paintEraser);
        paintEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(mBitmapSrc, 0, 0, paintEraser);
        paintEraser.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(),event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX+event.getX())/2;
                float endY = (mPreY+event.getY())/2;
                path.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY =event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }
}
