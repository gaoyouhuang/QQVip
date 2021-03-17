package com.example.myapplication.weight.dispatchtouch.upload;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class UpdateLinear extends LinearLayout {
    PersonAnimation personAnimation;
    LoadListener loadListener;

    public void setLoadListener(LoadListener loadListener) {
        this.loadListener = loadListener;
    }


    public UpdateLinear(Context context) {
        super(context);
        init();
    }

    public UpdateLinear(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UpdateLinear(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private static String TAG = "UpdateLinear";

    LayoutInflater inflater;
    View header;
    Scroller scroller;
    int headerHeight;

    /**
     * DEFAULT 默认
     * LESS_HEADER_HEIGHT 下拉高度小于header 回弹-0
     * MORE_HEADER_HEIGHT 下拉高度大于header 回弹-header高度
     * REFRESH 正在刷新
     */
    int DEFAULT = 1;
    int LESS_HEADER_HEIGHT = 2;
    int MORE_HEADER_HEIGHT = 3;
    int REFRESH = 4;

    int status = DEFAULT;

    float lastX;
    float lastY;

    private void init() {
        setOrientation(VERTICAL);
        inflater = LayoutInflater.from(getContext());
        scroller = new Scroller(getContext());
        header = inflater.inflate(R.layout.headerview_moren, this, false);
        post(new Runnable() {
            @Override
            public void run() {
                int headerWidth = header.getWidth();
                headerHeight = header.getHeight();
                MarginLayoutParams params = new LayoutParams(headerWidth, headerHeight);
                params.setMargins(0, -headerHeight, 0, 0);
                header.setLayoutParams(params);
            }
        });
        addView(header);

        Button button = new Button(getContext());
        button.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        button.setText("点我1");
        button.setGravity(Gravity.CENTER);
        addView(button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "55555", Toast.LENGTH_SHORT).show();
            }
        });
        Button button1 = new Button(getContext());
        button1.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        button1.setText("点我2");
        button1.setGravity(Gravity.CENTER);
        addView(button1);

        personAnimation = new LoadHeaderAnimation(header);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = false;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (status != DEFAULT && status != REFRESH) {
                    //就表示当前正在回弹的过程中，所以需要去停止回弹动画，继续执行下拉
                    if (!scroller.isFinished()) {
                        scroller.abortAnimation();
                    }
                }
                ret = false;
                Log.e(TAG, "UpdateLinear onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                //因为要拦截move事件，那么viewGroup的onTouchEvent可以去接受move事件
                float distanceX = ev.getX() - lastX;
                float distanceY = ev.getY() - lastY;
                //TODO 表示下拉 也就是会出现刷新的header
                if (distanceY > 0 && (Math.abs(distanceY) > Math.abs(distanceX))) {
                    ret = true;
                }else if (distanceY<=0&&(Math.abs(distanceY) > Math.abs(distanceX))){
                    ret = true;
                }
                Log.e(TAG, "UpdateLinear onInterceptTouchEvent ACTION_MOVE + distanceY " + distanceY);
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "UpdateLinear onInterceptTouchEvent ACTION_UP");
                ret = false;
                break;
        }
        lastX = ev.getX();
        lastY = ev.getY();
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "UpdateLinear onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "UpdateLinear onTouchEvent ACTION_MOVE " + getScrollY());
                float distenceY = event.getY() - lastY;
                Log.e(TAG, "UpdateLinear onTouchEvent ACTION_MOVE distenceY" + distenceY);

                if (getScrollY() > 0) {
//                    TODO 表示scroll在坐标轴之上
                    scrollBy(0, -(int) distenceY / 2);
                } else if (getScrollY() <= 0) {
//                    TODO 表示scroll在坐标轴之下
                    if (getScrollY() >= -headerHeight * 3) {
//                        TODO header高度以内回弹不刷新；大于header小于3倍的header则回弹加刷新
//                        TODO 大于3倍的header 不滑动了
                        if (getScrollY() >= -headerHeight) {
                            status = LESS_HEADER_HEIGHT;
                        } else {
                            status = MORE_HEADER_HEIGHT;
                        }
                        if (personAnimation != null)
                            personAnimation.dragAnimation(Math.abs(getScrollY()), headerHeight);
                        scrollBy(0, -(int) distenceY / 2);
                    } else {
                        status = MORE_HEADER_HEIGHT;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "UpdateLinear onTouchEvent ACTION_UP " + getScrollY());

                if (getScrollY() <= 0) {
//                    TODO 需要做回弹操作
                    if (status == LESS_HEADER_HEIGHT) {
                        if (personAnimation != null)
                            personAnimation.startAnimation(getScrollY(), -getScrollY(), headerHeight, 1000,false);
                        toScroll(0, -getScrollY());
                    } else if (status == MORE_HEADER_HEIGHT) {
                        status = REFRESH;
                        if (personAnimation != null)
                            personAnimation.startAnimation(getScrollY(), -headerHeight - getScrollY(), headerHeight, 1000,loadListener != null);
                        toScroll(0, -headerHeight - getScrollY());
                        if (loadListener != null)
                            loadListener.updateMore();
                    }
                }

                break;
        }
        lastY = event.getY();
        return true;
    }

    public void loadFinish() {
        if (personAnimation != null)
            personAnimation.endAnimation();
        toScroll(0, -getScrollY());
    }

    /**
     * startScroll用于一个平滑的滑动效果
     * 需要调用invalidate去刷新
     * 之后会讲整一个滑动过程分割成多个小段
     * 调用computeScroll方法去实现
     *
     * @param dx
     * @param dy dy 表示偏移量（也就是需要移动的距离）;上正下负
     */
    private void toScroll(int dx, int dy) {
        scroller.startScroll(0, getScrollY(), dx, dy, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    public interface PersonAnimation {
        void startAnimation(int startY, int endY, int unit, int time,boolean allrun);

        void dragAnimation(int distenceY, int unit);

        void endAnimation();
    }

    public interface LoadListener {
        void loadMore();

        void updateMore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        System.out.println("----------onSizeChange");
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
