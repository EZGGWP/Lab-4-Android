package com.itmo.lab4belov

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val data: List<Item>, val activity: MainActivity, val IVM: ItemViewModel, val rv: RecyclerView): RecyclerView.Adapter<Adapter.MyViewHolder>() {
     var vh: MyViewHolder? = null;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
         vh = MyViewHolder(layout, activity)

        return vh as MyViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, data[position]);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(val layout: View, val activity: MainActivity): RecyclerView.ViewHolder(layout) {
        fun bind(position: Int, item: Item): MyViewHolder {
            val name = layout.findViewById<TextView>(R.id.name);
            val desc = layout.findViewById<TextView>(R.id.desc);
            val priority = layout.findViewById<ImageView>(R.id.priority);

            name.text = item.name;
            desc.text = item.desc;

            if (item.priority) {
                priority.visibility = View.VISIBLE
            } else priority.visibility = View.GONE

            layout.setOnClickListener {
                val intent = Intent(layout.context, AddItem::class.java).putExtra("item", item);
                startActivityForResult(activity, intent, 1, null);
            }


            return MyViewHolder(layout, activity)
        }

    }
}