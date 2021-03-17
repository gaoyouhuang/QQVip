package com.example.myapplication.weight.beisaier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.myapplication.R;

public class BeiSaiErBallView extends View {

    /**
     * mPaintDefault 默认不拖拽
     * mPaintStill 拖拽 改变的默认原点圆点
     * mPaintMove 拖拽的中心园点
     * mPainText 文字
     */
    Paint mPaintDefault, mPaintStill, mPaintMove, mPaintText,mPaintBoom;
    int nowType = Ball_Default;
    //TODO 默认状态
    private static final int Ball_Default = 0;
    //TODO 拖拽状态
    private static final int Ball_DeMove = 1;
    //TODO 不相连 但还没释放状态
    private static final int Ball_NoConnect = 2;
    //TODO 爆炸状态
    private static final int Ball_Destroy = 3;
    private static final int Ball_Finish =4;

    //TODO 最大偏移量
    float mMaxDist;
    //TODO 气泡半斤
    float mBubbleRadius;
    int mWidth = 0;
    int mHeight = 0;

    //TODO 贝塞尔曲线
    Path mPath;

    //TODO 数字
    String str = "99";

    //TODO 文字矩阵
    Rect rectText;

    //TODO 圆心间距
    float mDist = 0;

    //TODO 圆的中心点
    PointF basePointF = new PointF();
    //TODO 移动的圆心
    PointF movePointF = new PointF();
    /**
     * 气泡爆炸的图片id数组
     */
    private int[] mBurstDrawablesArray = {R.drawable.burst_1, R.drawable.burst_2
            , R.drawable.burst_3, R.drawable.burst_4, R.drawable.burst_5};
    int burstIndex = 0;

    public BeiSaiErBallView(Context context) {
        super(context);
        init();
    }

    public BeiSaiErBallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BeiSaiErBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mBubbleRadius = 30;
        mMaxDist = mBubbleRadius * 6;

        mPaintDefault = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintDefault.setStyle(Paint.Style.FILL);
        mPaintDefault.setAntiAlias(true);
        mPaintDefault.setColor(Color.RED);

        mPaintStill = new Paint();
        mPaintStill.setAntiAlias(true);
        mPaintStill.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintStill.setColor(Color.RED);

        mPaintMove = new Paint();
        mPaintMove.setAntiAlias(true);
        mPaintMove.setColor(Color.GRAY);
        mPaintMove.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaintText = new Paint();
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(30);

        rectText = new Rect();

        mPath = new Path();

        mPaintBoom = new Paint();
        mPaintBoom.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        basePointF.x = w / 2;
        basePointF.y = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        先显示文字
        if (nowType == Ball_Default) {
            canvas.drawCircle(basePointF.x, basePointF.y, mBubbleRadius, mPaintDefault);
            mPaintText.getTextBounds(str, 0, str.length(), rectText);
            canvas.drawText(str, basePointF.x - rectText.width() / 2, basePointF.y + rectText.height() / 2, mPaintText);
        } else if (nowType == Ball_DeMove) {

            float mStillRadius = (float) (mBubbleRadius - mDist * 20 / mMaxDist - 2);

            //计算两圆心的中心点
            int iAnchorX = (int) ((basePointF.x + movePointF.x) / 2);
            int iAnchorY = (int) ((basePointF.y + movePointF.y) / 2);

            //三角函数
            float cosTheta = (float) ((movePointF.x - basePointF.x) / mDist);
            float sinTheta = (float) ((movePointF.y - basePointF.y) / mDist);

            //A
            float iBubStillStartX = basePointF.x - mStillRadius * sinTheta;
            float iBubStillStartY = basePointF.y + mStillRadius * cosTheta;
            //B
            float iBubMoveableEndX = movePointF.x - mBubbleRadius * sinTheta;
            float iBubMoveableEndY = movePointF.y + mBubbleRadius * cosTheta;
            //C
            float iBubMoveableStartX = movePointF.x + mBubbleRadius * sinTheta;
            float iBubMoveableStartY = movePointF.y - mBubbleRadius * cosTheta;
            //D
            float iBubStillEndX = basePointF.x + mStillRadius * sinTheta;
            float iBubStillEndY = basePointF.y - mStillRadius * cosTheta;

            //贝塞尔曲线
            mPath.reset();
            canvas.save();
            // 画上半弧
            mPath.moveTo(iBubStillStartX, iBubStillStartY);
            mPath.quadTo(iAnchorX, iAnchorY, iBubMoveableEndX, iBubMoveableEndY);
            // 画下半弧
            mPath.lineTo(iBubMoveableStartX, iBubMoveableStartY);
            mPath.quadTo(iAnchorX, iAnchorY, iBubStillEndX, iBubStillEndY);
            //闭合PATH
            mPath.close();
            canvas.drawPath(mPath, mPaintDefault);
            canvas.restore();
            //不动的圆
            canvas.save();
            canvas.drawCircle(basePointF.x, basePointF.y, mStillRadius, mPaintStill);
            canvas.restore();

            //动的圆
            canvas.save();
            canvas.drawCircle(movePointF.x, movePointF.y, mBubbleRadius, mPaintMove);
            mPaintText.getTextBounds(str, 0, str.length(), rectText);
            canvas.drawText(str, movePointF.x - rectText.width() / 2, movePointF.y + rectText.height() / 2, mPaintText);
            canvas.restore();
        } else if (nowType == Ball_NoConnect) {
            //动的圆
            canvas.save();
            canvas.drawCircle(movePointF.x, movePointF.y, mBubbleRadius, mPaintMove);
            mPaintText.getTextBounds(str, 0, str.length(), rectText);
            canvas.drawText(str, movePointF.x - rectText.width() / 2, movePointF.y + rectText.height() / 2, mPaintText);
            canvas.restore();

        } else if (nowType == Ball_Destroy) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBurstDrawablesArray[burstIndex], options);
            Rect rect = new Rect((int)(movePointF.x - mBubbleRadius), (int)(movePointF.y - mBubbleRadius),
                    (int)(movePointF.x + mBubbleRadius), (int)(movePointF.y + mBubbleRadius));
            canvas.drawBitmap(bitmap, null, rect,mPaintBoom);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                movePointF.x = event.getX();
                movePointF.y = event.getY();

                mDist = (float) Math.hypot(Math.abs(x - basePointF.x - mBubbleRadius / 2), Math.abs(y - basePointF.y - mBubbleRadius / 2));
                if (mDist > 2 * mBubbleRadius) {
                    nowType = Ball_Default;
                } else {
                    nowType = Ball_DeMove;
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_MOVE:

                movePointF.x = event.getX();
                movePointF.y = event.getY();
                mDist = (float) Math.hypot(Math.abs(x - basePointF.x - mBubbleRadius / 2), Math.abs(y - basePointF.y - mBubbleRadius / 2));

                if (mDist > mMaxDist) {
                    nowType = Ball_NoConnect;
                } else {
                    nowType = Ball_DeMove;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                movePointF.x = event.getX();
                movePointF.y = event.getY();
                mDist = (float) Math.hypot(Math.abs(x - basePointF.x - mBubbleRadius / 2), Math.abs(y - basePointF.y - mBubbleRadius / 2));

                if (mDist > mMaxDist) {
                    nowType = Ball_Destroy;
                    startBubbleBurstAnim();
                } else {
                    nowType = Ball_DeMove;
                    startBubbleRestAnim();
                }
                invalidate();
                break;
        }

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startBubbleRestAnim() {

        ValueAnimator anim = ValueAnimator.ofObject(new PointFEvaluator(),
                new PointF(movePointF.x, movePointF.y),
                new PointF(basePointF.x, basePointF.y));

        anim.setDuration(200);
        anim.setInterpolator(new OvershootInterpolator(5f));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                movePointF = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                nowType = Ball_Default;
            }
        });

        anim.start();


    }


    private void startBubbleBurstAnim() {
        //气泡改为消失状态
        //做一个int型属性动画，从0~mBurstDrawablesArray.length结束
        ValueAnimator anim = ValueAnimator.ofInt(0, mBurstDrawablesArray.length);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(500);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //设置当前绘制的爆炸图片index
                burstIndex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //修改动画执行标志
                nowType = Ball_Finish;
            }
        });
        anim.start();

    }

}
