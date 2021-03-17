package com.example.myapplication.weight.chongtu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

public class ChongTuActivity extends AppCompatActivity {
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chong_tu);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return super.canScrollVertically();
            }
        });
        MyAdapter myAdapter = new MyAdapter(this);
        rv.setAdapter(myAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapterItem> {
        Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public MyAdapterItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyAdapterItem(LayoutInflater.from(context).inflate(R.layout.item_demo, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapterItem holder, int position) {
            holder.tv.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

    private class MyAdapterItem extends RecyclerView.ViewHolder {
        TextView tv;

        public MyAdapterItem(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
