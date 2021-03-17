package com.example.myapplication.weight.rv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MyIntentService;
import com.example.myapplication.R;

import java.util.logging.LoggingMXBean;

public class Main9Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setAdapter(new Myadapter(this));
        rv.setLayoutManager(new WaterfallLayoutManager());
        method();
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("msg","hahaha");
        startService(intent);
    }

    public void method() {
        Bean bean = new Bean();
        bean.name = "1111";

        Bean bean1 = bean;
        System.out.println("Bean1.name " + bean1.name);
        System.out.println("Bean.name " + bean.name);

        bean1.name = "2222";
        System.out.println("change -------------");
        System.out.println("Bean1.name " + bean1.name);
        System.out.println("Bean.name " + bean.name);

    }

    public class Bean {
        String name;
    }
}
