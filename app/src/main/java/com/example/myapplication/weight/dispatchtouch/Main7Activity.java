package com.example.myapplication.weight.dispatchtouch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.weight.dispatchtouch.tools.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class Main7Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        recyclerView = findViewById(R.id.recycle);
        Adapter adapter = new Adapter(getApplicationContext());
        List<String> strings = new ArrayList<>();
        for (int i = 0;i<20;i++){
            strings.add(i+"号");
        }
        adapter.setData(strings);
        MyCallBack myCallBack = new MyCallBack(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(myCallBack);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
