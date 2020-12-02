package com.itmo.lab4belov

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DBO {
    @Insert
    fun insert(item: Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("DELETE FROM items")
    fun clear()

    @Query("SELECT * FROM items ORDER BY PRIORITY DESC")
    fun getAll() : LiveData<List<Item>>
}