package com.example.myapplication.weight.dispatchtouch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemHolder> {

    LayoutInflater inflater;
    Context context;
    List<String> strings;

    public Adapter(Context applicationContext) {
        inflater = LayoutInflater.from(applicationContext);
        this.context = applicationContext;
        strings = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.item_demo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.tv.setText(strings.get(position));
        holder.tvDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"dele",Toast.LENGTH_LONG).show();
            }
        });
        holder.tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"hello",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public void setData(List<String> strings) {
        this.strings = new ArrayList<>();
        this.strings.addAll(strings);
        notifyDataSetChanged();
    }

    public void onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int onClickItem = viewHolder.getLayoutPosition();
        int targetItem = target.getLayoutPosition();
        notifyItemMoved(onClickItem, targetItem);
    }

    public void onDele(RecyclerView.ViewHolder layoutPosition) {
        int deleIndex = layoutPosition.getLayoutPosition();
        strings.remove(deleIndex);
        notifyItemRemoved(deleIndex);
    }


   public class ItemHolder extends RecyclerView.ViewHolder {
       public TextView tv, tvDele, tvHello;
       public LinearLayout llLeft;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            llLeft = itemView.findViewById(R.id.ll_left);
            tvDele = itemView.findViewById(R.id.tv_dele);
            tvHello = itemView.findViewById(R.id.tv_hello);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
