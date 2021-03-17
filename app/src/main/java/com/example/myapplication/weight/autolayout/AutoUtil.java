package com.example.myapplication.weight.autolayout;

import android.accessibilityservice.FingerprintGestureController;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class AutoUtil {

    private static AutoUtil autoUtil;

    public static AutoUtil getInstance() {
        if (autoUtil == null)
            autoUtil = AutoUtil_Inner.autoUtil;
        return autoUtil;
    }

    private static class AutoUtil_Inner {
        public static AutoUtil autoUtil = new AutoUtil();
    }

    private AutoUtil() {
    }

    float UIwidth = 320;


    private void autoLayout() {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();

        float appDensity = displayMetrics.widthPixels / UIwidth;
        float appDensityDpi = appDensity * 160;
        float appScaledDensity = appDensity * (displayMetrics.scaledDensity / displayMetrics.density);

        displayMetrics.density = appDensity;
        displayMetrics.densityDpi = (int) appDensityDpi;
        displayMetrics.scaledDensity = appScaledDensity;

    }

    Application application;
      Activity activity;

    public void init(Application application, Activity activity) {
        this.activity = activity;
        this.application = application;
        autoLayout();
    }

}
