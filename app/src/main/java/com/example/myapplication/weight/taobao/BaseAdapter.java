package com.example.myapplication.weight.taobao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;

public class BaseAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {

    Context context;
    int layoutId = -1;
    int count = -1;
    int viewType = -1;
    LayoutHelper layoutHelper;

    public BaseAdapter(Context context, int layoutId, int count, LayoutHelper layoutHelper,int viewType) {
        this.context = context;
        this.layoutId = layoutId;
        this.count = count;
        this.viewType = viewType;
        this.layoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (this.viewType == viewType)
            return new BaseViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @Override
    public int getItemCount() {
        return count;
    }
}
