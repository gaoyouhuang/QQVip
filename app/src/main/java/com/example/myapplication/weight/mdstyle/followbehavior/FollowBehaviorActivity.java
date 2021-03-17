package com.example.myapplication.weight.mdstyle.followbehavior;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class FollowBehaviorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_behavior);
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("------------");
                System.out.println("getX " + v.getX());
                System.out.println("getY " + v.getY());
                System.out.println("getWidth " + v.getWidth());
                System.out.println("getHeight " + v.getHeight());
                System.out.println("getRawX " + event.getRawX());
                System.out.println("getRawY " + event.getRawY());
                System.out.println("getX " + event.getX());
                System.out.println("getY " + event.getY());
                v.setX(event.getRawX()-v.getWidth()/2);
                v.setY(event.getRawY()-v.getHeight()/2-getStatusBar());
                return true;
            }
        });
    }

    public int getStatusBar() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int dimensionPixelSize = getResources().getDimensionPixelSize(identifier);
        System.out.println("status_bar " + dimensionPixelSize);
        return dimensionPixelSize;
    }
}
