package com.itmo.lab4belov

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.*

@Database(entities = [Item::class], version = 1)
abstract class Db: RoomDatabase() {
    abstract fun dbo(): DBO

    companion object {
        @Volatile
        private var INSTANCE: Db? = null

        fun getDb(context: Context, scope: CoroutineScope): Db {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, Db::class.java, "items_db")
                    .fallbackToDestructiveMigration().build();
                INSTANCE = instance;
                instance
            }
        }

        fun fillDb(dbo: DBO) {
            dbo.insert(Item("test", "SOme testing data", true));
        }

        private class DbCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let{database -> scope.launch(Dispatchers.IO) {
                        fillDb(database.dbo());
                    }
                }
            }
        }
    }
}