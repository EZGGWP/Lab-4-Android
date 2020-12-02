package com.itmo.lab4belov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val itemViewModel: ItemViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter: Adapter;

        itemViewModel.data.observe(this, {
            items -> adapter = Adapter(items, this, itemViewModel, findViewById(R.id.recycler));
            var rv = findViewById<RecyclerView>(R.id.recycler);
            rv.adapter = adapter;

            val itemTouchHelperCallback =
                    object :
                            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                        override fun onMove(
                                recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder
                        ): Boolean {

                            return false
                        }

                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            itemViewModel.delete(itemViewModel.getItemAt(viewHolder.adapterPosition));
                        }

                    }

            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(rv)

            adapter.notifyDataSetChanged();
        })
        val addButton = findViewById<FloatingActionButton>(R.id.add_button);
        addButton.setOnClickListener {
            val intent = Intent(this, AddItem::class.java);
            startActivityForResult(intent, 1);
            // TODO: Вызвать стартФорРезулт из адаптера
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            Log.d("UPDATE", "No data found");
            return;
        } else {
            val updateSwitch = data.getBooleanExtra("update", false);
            Log.d("UPDATE SWITCH AT INSERTION", updateSwitch.toString());
            if (updateSwitch) {
                Log.d("Trying to update item ", (data.getSerializableExtra("item") as Item).name + ", " + (data.getSerializableExtra("item") as Item).desc + ", " + (data.getSerializableExtra("item") as Item).id);
                itemViewModel.update(data.getSerializableExtra("item") as Item);
            } else {
                Log.d("Trying to insert item ", (data.getSerializableExtra("item") as Item).name + ", " + (data.getSerializableExtra("item") as Item).desc);
                itemViewModel.insert(data.getSerializableExtra("item") as Item)
            };
            Toast.makeText(applicationContext, "Item added", Toast.LENGTH_SHORT).show();
        }
    }

}