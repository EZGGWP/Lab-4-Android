package com.itmo.lab4belov

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.checkbox.MaterialCheckBox

class AddItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item_layout);
        if (intent.hasExtra("item")) {
            val item : Item = intent.getSerializableExtra("item") as Item;
            findViewById<EditText>(R.id.nameAdd).setText(item.name);
            findViewById<EditText>(R.id.descAdd).setText(item.desc);
            findViewById<MaterialCheckBox>(R.id.priorityCB).isChecked = item.priority;
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu) // True?
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_icon -> {
                if (intent.hasExtra("item")) {
                    saveItem(true);
                } else saveItem(false);
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun saveItem(update: Boolean) {
        val name = findViewById<EditText>(R.id.nameAdd);
        val desc = findViewById<EditText>(R.id.descAdd);
        val priority = findViewById<MaterialCheckBox>(R.id.priorityCB);
        if (name.text.toString() == "") {
            Toast.makeText(applicationContext, "Name can't be empty", Toast.LENGTH_LONG).show();
        } else {
            val resultIntent : Intent;
            if (update) {
                val itemName = name.text.toString();
                val itemDesc = desc.text.toString();
                val itemPriority = priority.isChecked;
                val newItem = Item(itemName, itemDesc, itemPriority);
                newItem.id = (intent.getSerializableExtra("item") as Item).id;
                resultIntent = Intent().putExtra("item", newItem).putExtra("update", update);
                Log.d("Resulting intent update value:", resultIntent.getBooleanExtra("update", false).toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                val itemName = name.text.toString();
                val itemDesc = desc.text.toString();
                val itemPriority = priority.isChecked;
                val newItem = Item(itemName, itemDesc, itemPriority);
                resultIntent = Intent().putExtra("item", newItem).putExtra("update", update);
                Log.d("Resulting intent update value:", resultIntent.getBooleanExtra("update", false).toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    }
}