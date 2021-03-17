package com.example.myapplication.weight.dispatchtouch.onintercept;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

public class DemoButton extends AppCompatButton {
    private static String TAG ="intercept";
    public DemoButton(Context context) {
        super(context);
    }

    public DemoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"DemoButton onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"DemoButton onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"DemoButton onTouchEvent ACTION_MOVE");
                break;
        }
        return true;
    }
}
