package com.example.myapplication.weight.qqheader;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.R;

public class QQHeaderSrollView extends ListView {

    private static final String TAG = "david";
    private ImageView mImageView;
    private int mImageViewHeight;//初始高度

    public void setZoomImageView(ImageView iv) {
        mImageView = iv;
    }
    public QQHeaderSrollView(Context context) {
        super(context);
    }

    public QQHeaderSrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mImageViewHeight=context.getResources().getDimensionPixelSize(R.dimen.size_default_height);
    }

    public QQHeaderSrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(action== MotionEvent.ACTION_UP){
            ResetAnimation resetAnimation = new ResetAnimation(mImageViewHeight);
            resetAnimation.setInterpolator(new OvershootInterpolator());
            resetAnimation.setDuration(700);
            mImageView.startAnimation(resetAnimation);
        }
        return super.onTouchEvent(ev);
    }

    public class ResetAnimation extends Animation {

        private int extraHeight;
        private int currentHeight;

        public ResetAnimation(int targetHeight) {
            currentHeight = mImageView.getHeight();
            extraHeight = mImageView.getHeight() - targetHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            mImageView.getLayoutParams().height = (int) (currentHeight - extraHeight * interpolatedTime);
            mImageView.requestLayout();
            super.applyTransformation(interpolatedTime, t);
        }
    }
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        Log.i(TAG, "overScrollBy: "+deltaY);
        if (deltaY < 0) {
//            -  下拉
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
            mImageView.requestLayout();
        }else {
//            + 上拉
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
            mImageView.requestLayout();
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


    //
}
