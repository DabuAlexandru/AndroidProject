package com.android.example.androidproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MemoryEntity::class], version = 1, exportSchema = false)
abstract class MemoriesDatabase  : RoomDatabase(){
    abstract val memoriesDatabaseDao: MemoriesDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MemoriesDatabase? = null

        fun getInstance(context: Context): MemoriesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MemoriesDatabase::class.java,
                        "memories_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}