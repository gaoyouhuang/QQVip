package com.example.myapplication.weight.viewpager2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.myapplication.R;

public class ViewPager2Activity extends AppCompatActivity {
ViewPager2 viewpager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);
        viewpager2 = findViewById(R.id.viewpager2);
//        viewpager2.setAdapter();
    }
}
