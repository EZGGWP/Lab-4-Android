package com.itmo.lab4belov

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items")
class Item (val name: String, val desc: String, val priority: Boolean): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0;
}