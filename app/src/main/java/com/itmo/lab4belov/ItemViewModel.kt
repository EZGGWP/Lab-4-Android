package com.itmo.lab4belov

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Storage(app.applicationContext, viewModelScope)

    val data = repo.data;

    fun insert(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(item)
    }

    fun update(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        repo.update(item)
    }

    fun delete(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        repo.delete(item)
    }

    fun getItemAt(at: Int): Item {
        return data.value?.get(at)!!;
    }

}