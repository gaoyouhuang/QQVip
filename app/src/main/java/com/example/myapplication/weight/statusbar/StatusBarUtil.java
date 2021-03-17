package com.example.myapplication.weight.statusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;

public class StatusBarUtil {

//TODO 5.0以上        activity.getWindow().setStatusBarColor();

//TODO 或者通过 重写setContentView 通过布局去控制状态栏 前提是沉浸式||全面屏
//    @Override
//    public void setContentView(int layoutResID) {
//        super.setContentView(R.layout.activity_base);
//        mNotchContainer = findViewById(R.id.notch_container);
//        mNotchContainer.setTag("notch_container");
//        mContentContainer = findViewById(R.id.content_container);
//        LayoutInflater.from(this).inflate(layoutResID, mContentContainer, true);
//    }
    public static void setStatusBar(Activity activity, Toolbar toolbar) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int count = decorView.getChildCount();
        if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
            View view = decorView.getChildAt(count - 1);
            view.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }

        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.TRANSPARENT);

        decorView.addView(statusBarView);

        if (toolbar != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            layoutParams.setMargins(0, getStatusBarHeight(activity), 0, 0);
            toolbar.setLayoutParams(layoutParams);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int resid = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int height = context.getResources().getDimensionPixelOffset(resid);
        if (height <= 0)
            return 0;
        else
            return height;
    }
    /**
     * 代码方法 取消状态栏
     *
     * @param activity
     */
    public static void setNoStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
