package com.itmo.lab4belov

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope

class Storage(context: Context, scope: CoroutineScope) {
    private val dbo: DBO;
    val data: LiveData<List<Item>>;

    init {
        val db: Db = Db.getDb(context, scope);
        dbo = db.dbo();
        data = dbo.getAll();
    }

    fun insert(item: Item) {
        dbo.insert(item);
    }

    fun update(item: Item) {
        dbo.update(item)
    }

    fun delete(item: Item) {
        dbo.delete(item)
    }

}