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
import android.graphics.Rect;
import android.media.MediaDrm;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;


public class GuaGuaLeView extends View {
    public GuaGuaLeView(Context context) {
        super(context);
        init();
    }

    public GuaGuaLeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuaGuaLeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Path mPath;
    Paint paintGuaGuaLe;
    Bitmap mBitmapDst, mBitmapText, mBitmapSrc;
    float lastX, lastY;

    public void init() {

        mPath = new Path();

        paintGuaGuaLe = new Paint();
        paintGuaGuaLe.setStrokeWidth(50);
        paintGuaGuaLe.setColor(Color.parseColor("#FF00FF"));
        paintGuaGuaLe.setStyle(Paint.Style.STROKE);

        mBitmapText = BitmapFactory.decodeResource(getResources(), R.mipmap.guaguaka_text1);
        mBitmapSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.guaguaka);
        mBitmapDst = Bitmap.createBitmap(mBitmapSrc.getWidth(), mBitmapSrc.getHeight(), Bitmap.Config.ARGB_4444);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmapText, 0, 0, paintGuaGuaLe);
        Log.i("haha",canvas.getSaveCount()+"");
        int layerId = canvas.saveLayer(0, 0, mBitmapSrc.getWidth(), mBitmapSrc.getHeight(),
                null, Canvas.ALL_SAVE_FLAG);
//        Canvas c = new Canvas(mBitmapDst);
        canvas.drawPath(mPath,paintGuaGuaLe);
//        canvas.drawBitmap(mBitmapDst,0,0,paintGuaGuaLe);
        paintGuaGuaLe.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(mBitmapSrc,0,0,paintGuaGuaLe);
        paintGuaGuaLe.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(),event.getY());
                lastX = event.getX();
                lastY = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                mPath.quadTo(lastX,lastY,(lastX+event.getX())/2,(event.getY()+lastY)/2);
                lastX = event.getX();
                lastY = event.getY();
                break;

        }
        postInvalidate();
        return super.onTouchEvent(event);
    }
}
