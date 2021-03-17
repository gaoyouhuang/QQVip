package com.example.myapplication.weight.xformode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class WareView extends View {

    public WareView(Context context) {
        super(context);
        init();

    }

    public WareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public WareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Bitmap mBitmapSrc, mBitmapDst;
    Paint paint;
    int bitmapWidth;
    int dx = 0;

    public void init() {
        mBitmapDst = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_shape);
        mBitmapSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.wav);
        paint = new Paint();
        bitmapWidth = mBitmapSrc.getWidth();
        post(new Runnable() {
            @Override
            public void run() {
                dx += 5;
                if (dx >= bitmapWidth)
                    dx = 0;
                invalidate();
                postDelayed(this, 50);
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBitmapDst, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBitmapSrc, new Rect(dx, 0, dx + mBitmapDst.getWidth(), mBitmapDst.getHeight()), new Rect(0, 0, mBitmapDst.getWidth(), mBitmapDst.getHeight()), paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }
}
