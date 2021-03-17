package com.example.myapplication.weight.dispatchtouch.upload;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class LoadHeaderAnimation implements UpdateLinear.PersonAnimation {

    View headerView;
    ImageView reflushImg;
    TextView tvMsg;
    ValueAnimator valueAnimator;


    public LoadHeaderAnimation(View headerView) {
        this.headerView = headerView;
        reflushImg = headerView.findViewById(R.id.iv);
        tvMsg = headerView.findViewById(R.id.tv);
    }

    @Override
    public void startAnimation(int startY, int endY, final int unit, int time,boolean allrun) {
        float distanceY = Math.abs(startY - endY);
        valueAnimator = ValueAnimator.ofFloat(distanceY, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float nowDistance = (float) animation.getAnimatedValue();
                float router = nowDistance % unit / unit * 360;
                reflushImg.setRotation(router);
                System.out.println("run run run");
            }
        });
        valueAnimator.setDuration(time);
        valueAnimator.setRepeatCount(allrun?ValueAnimator.INFINITE:1);
        valueAnimator.start();
    }

    @Override
    public void dragAnimation(int distenceY, int unit) {
        float router = (float) distenceY % unit / unit * 360;
        reflushImg.setRotation(router);
    }

    @Override
    public void endAnimation() {
        System.out.println("animation finish");
        if (valueAnimator != null)
            if (valueAnimator.isRunning())
                valueAnimator.end();
    }
}
