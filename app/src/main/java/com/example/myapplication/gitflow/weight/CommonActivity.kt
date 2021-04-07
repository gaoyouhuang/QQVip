package com.example.myapplication.gitflow.weight

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCommonBinding
import com.example.myapplication.gitflow.weight.update.UpdateViewGroup

class CommonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCommonBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_common)
        binding.rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = MyAdapter(this)
        binding.update.listener = object : UpdateViewGroup.UpdateListener {
            override fun loadMore() {
                handler.postDelayed({
                    binding.update.loadOver()
                }, 2000)
            }
        }
    }

    var my:UpdateViewGroup.UpdateListener = object :UpdateViewGroup.UpdateListener {
        override fun loadMore() {
            TODO("Not yet implemented")
        }
    }

    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }

    class MyAdapter(var context: Context) : RecyclerView.Adapter<MyAdapter.MyItem>() {
        var list: List<String> = List(20) { it.toString() }

        var layoutInterface: LayoutInflater = LayoutInflater.from(context)

        inner class MyItem(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            var tv: TextView = viewItem.findViewById(R.id.Item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItem =
                MyItem(layoutInterface.inflate(R.layout.item, parent, false))

        override fun onBindViewHolder(holder: MyItem, position: Int) {
            holder.tv.text = list[position]
        }

        override fun getItemCount(): Int = list.size
    }
}