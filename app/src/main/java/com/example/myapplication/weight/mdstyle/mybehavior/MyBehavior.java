package com.example.myapplication.weight.mdstyle.mybehavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

public class MyBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private static String TAG = "Mybehavior";
    int distenceY = 0;

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull Toolbar child, @NonNull View dependency) {
        return dependency instanceof NestedScrollView;

    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull Toolbar child, @NonNull View dependency) {
        if (distenceY == 0) {
            distenceY = dependency.getTop();
            Log.i(TAG, distenceY + " "+ child.getHeight());
        }
        int _transY = (int) ((float)dependency.getTop()/distenceY*child.getHeight());
        Log.i(TAG, _transY+" ");
        _transY = _transY>child.getHeight()?child.getHeight():_transY;
        child.setTranslationY(-_transY);
        return true;

    }
}
