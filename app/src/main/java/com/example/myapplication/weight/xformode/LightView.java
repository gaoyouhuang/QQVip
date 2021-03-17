package com.example.myapplication.weight.xformode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class LightView extends View {
    public LightView(Context context) {
        super(context);
        init();
    }

    public LightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    Bitmap mBitmapSrc, mBitmapDst;
    Paint paint;

    public void init() {
        mBitmapDst = BitmapFactory.decodeResource(getResources(), R.mipmap.book_bg);
        mBitmapSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.book_light);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerId = canvas.saveLayer(0, 0, mBitmapDst.getWidth(), mBitmapDst.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBitmapDst, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        canvas.drawBitmap(mBitmapSrc, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}
