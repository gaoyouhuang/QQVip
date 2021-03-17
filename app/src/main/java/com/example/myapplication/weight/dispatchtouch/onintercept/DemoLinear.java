package com.example.myapplication.weight.dispatchtouch.onintercept;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class DemoLinear extends LinearLayout {
    private static String TAG = "intercept";

    public DemoLinear(Context context) {
        super(context);
    }

    public DemoLinear(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoLinear(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ret = false;
                Log.e(TAG,"DemoLinear onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"DemoLinear onInterceptTouchEvent ACTION_MOVE");
                ret = true;
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"DemoLinear onInterceptTouchEvent ACTION_MOVE");
                ret = false;
                break;
        }
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"DemoLinear onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"DemoLinear onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"DemoLinear onTouchEvent ACTION_MOVE");
                break;
        }
        return true;
    }
}
