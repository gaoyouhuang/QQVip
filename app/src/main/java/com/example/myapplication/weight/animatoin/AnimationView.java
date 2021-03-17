package com.example.myapplication.weight.animatoin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import java.lang.reflect.Method;

public class AnimationView extends View {
    float[] roundWeights = new float[]{0, 0.2f, 0.4f, 0.6f, 0.8f};
    int[] roundColors = new int[]{Color.RED, Color.GRAY, Color.YELLOW, Color.BLUE, Color.BLACK};
    Paint paintRound, paintBackGround;
    PointF basePointF = new PointF();
    DrawType drawType;

    float bigRound = 150f;
    float smallRound = 30f;

    Path path;
    PathMeasure pathMeasure;

    float[] pos = new float[2];
    float[] tan = new float[2];

    ValueAnimator valueAnimator;
    float progress = 0;
    float diagonal;

    public AnimationView(Context context) {
        super(context);
        init();
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paintRound = new Paint();
        paintRound.setStyle(Paint.Style.FILL);

        paintBackGround = new Paint();
        paintBackGround.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        basePointF.x = w / 2;
        basePointF.y = h / 2;
        diagonal = (float) Math.sqrt(Math.pow((double) (w), 2) + Math.pow((double) (h), 2)) / 2;
        Log.e("myView", diagonal + " w " + w + " h " + h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawType == null) {
            drawType = new TurnAroundDraw();
        }
        drawType.draw(canvas);
    }

    private void drawBackground(Canvas canvas) {
        path = new Path();
        if (drawType != null && drawType instanceof DiffusionDraw) {
            paintBackGround.setColor(Color.WHITE);
            float stroke = diagonal - bigRound;
            paintBackGround.setStrokeWidth(stroke);
            canvas.drawCircle(basePointF.x, basePointF.y, bigRound + stroke / 2, paintBackGround);
        } else {
            canvas.drawColor(Color.WHITE);
            path.addCircle(basePointF.x, basePointF.y, bigRound, Path.Direction.CW);
            paintRound.setColor(Color.WHITE);
            canvas.drawPath(path, paintRound);
        }

    }



    private void drawRound(Canvas canvas) {
        pathMeasure = new PathMeasure(path, true);
        float length = pathMeasure.getLength();

        for (int i = 0; i < roundWeights.length; i++) {
            float distance = getDistance(roundWeights[i], length, progress);
            boolean posTan = pathMeasure.getPosTan(distance, pos, tan);
            paintRound.setColor(roundColors[i]);
            canvas.drawCircle(pos[0], pos[1], smallRound, paintRound);
        }
    }

    private float getDistance(float weight, float length, float progress) {
        float now = (weight + progress > 1 ? weight + progress - 1 : weight + progress) * length;
        return now;
    }

    /**
     * 旋转动画
     */
    private class TurnAroundDraw implements DrawType {
        public TurnAroundDraw() {
            valueAnimator = ValueAnimator.ofFloat(0f, 1);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float _progress = (float) animation.getAnimatedValue();
                    progress = _progress;
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    drawType = new AggregationDraw();
                    invalidate();
                }
            });
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(1000);
            valueAnimator.setRepeatCount(5);
            valueAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            drawBackground(canvas);
            drawRound(canvas);
        }
    }


    /**
     * 聚合动画
     */
    private class AggregationDraw implements DrawType {
        public AggregationDraw() {
            valueAnimator = ValueAnimator.ofFloat(150f, 0f);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    bigRound = Math.abs((float) animation.getAnimatedValue());
                    System.out.println("bigRound  "+bigRound);
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    drawType = new DiffusionDraw();
                    invalidate();
                }
            });
            valueAnimator.setInterpolator(new OvershootInterpolator(10));
            valueAnimator.setDuration(1000);
            valueAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            drawBackground(canvas);
            drawRound(canvas);
        }
    }
    /**
     * 扩散动画
     */
    private class DiffusionDraw implements DrawType {
        public DiffusionDraw() {
            valueAnimator = ValueAnimator.ofFloat(0f, diagonal);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    bigRound = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(5000);
            valueAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            drawBackground(canvas);
        }
    }

    private interface DrawType {
        void draw(Canvas canvas);

    }
}
