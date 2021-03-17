package com.example.myapplication.weight.dispatchtouch.upload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

import com.example.myapplication.R;

import java.lang.annotation.Native;

public class UpdateActivity extends AppCompatActivity {
    UpdateLinear updateLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        updateLinear = findViewById(R.id.update);
        updateLinear.setLoadListener(new UpdateLinear.LoadListener() {
            @Override
            public void loadMore() {

            }

            @Override
            public void updateMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateLinear.loadFinish();
                    }
                }, 2000);
            }
        });

        Bean bean = new Bean();
        Bean bean1 = bean;
        System.out.println((bean == bean1) + " hh");
        System.out.println(bean.hashCode() + " hh");
        System.out.println(bean1.hashCode() + " hh");
        bean1.name = "22";
        System.out.println(bean1.name);
        System.out.println(bean.name);

    }

    class Bean {
        public String name = "1";
    }

    Handler handler = new Handler() {
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
        }
    };
}
