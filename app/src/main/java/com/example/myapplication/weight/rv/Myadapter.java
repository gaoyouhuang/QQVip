package com.example.myapplication.weight.rv;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;
import java.util.Random;

/**
 * Created by txw_pc on 2017/4/21.
 */

public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {
    String[] data = new String[300];
    Activity activity;
    int[] heights = new int[]{100, 150, 200, 250};
    int[] colors = new int[]{Color.GRAY, Color.YELLOW, Color.BLUE, Color.RED};

    public Myadapter(Activity activity) {
        this.activity = activity;
        initData();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        Display defaultDisplay = activity.getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        float itemWidth = (float) metrics.widthPixels / 3;

        layoutParams.width = (int) itemWidth;
        int randNum  =position%4;
        layoutParams.height =  heights[randNum];

        holder.btn.setText(  heights[randNum] + " "+position);
        holder.btn.setBackgroundColor(colors[randNum]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn);
        }
    }

    private void initData() {
        String[] str = new String[]{"adf", "gfgfadfaf", "gfgfadfafadf", "gfgfadfafdfa", "gfgfadfafadffad", "gfgfadfafadfasfsfd", "gfg", "gfgfadfafadfadfafadfa"};
        for (int i = 0; i < data.length; i++) {
            data[i] = str[(int) (Math.random() * str.length)];
        }
    }
}
