package com.example.myapplication.weight.qqheader.md;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.example.myapplication.R;

public class QQNestedScroll extends NestedScrollView {
    public QQNestedScroll(@NonNull Context context) {
        this(context, null);
    }

    public QQNestedScroll(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQNestedScroll(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Scroller scroller;
    View header;
    ImageView imageView;
    LinearLayout root;
    int defaultImg = 0;

    private void init() {
        header = View.inflate(getContext(), R.layout.listview_header, null);
        imageView = header.findViewById(R.id.layout_header_image);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                defaultImg = imageView.getHeight();
            }
        });
        root = new LinearLayout(getContext());
        root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        root.setOrientation(LinearLayout.VERTICAL);
        root.addView(header);
        scroller = new Scroller(getContext());
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        root.addView(child);
        super.addView(root, params);
    }

    float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("QQNestedScroll", "top " + imageView.getTop());
                if (imageView.getTop() >= 0) {
                    float distanceY = ev.getRawY() - lastY;
                    Log.e("QQNestedScroll", "distanceY " + distanceY);
                    if (distanceY > 0 && distanceY>ViewConfiguration.get(getContext()).getScaledTouchSlop()/2) {
                        if (imageView.getHeight() <= 2 * defaultImg) {
                            imageView.getLayoutParams().height = (int) (distanceY / 2 + imageView.getHeight());
                            imageView.requestLayout();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (imageView.getLayoutParams().height > defaultImg)
                    startAnimotoin(imageView.getLayoutParams().height, defaultImg);
                break;
        }
        lastY = ev.getRawY();
        return super.onTouchEvent(ev);
    }

    public void startAnimotoin(int startHeight, int endHeight) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imageView.getLayoutParams().height = (int) animation.getAnimatedValue();
                imageView.requestLayout();
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }
}
