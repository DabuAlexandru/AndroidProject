package com.android.example.androidproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MemoriesDatabaseDao {
    @Insert
    fun insert(night: MemoryEntity)

    @Update
    fun update(night: MemoryEntity)

    @Query("SELECT * from memories_table WHERE memoryId = :key")
    fun get(key: Long): MemoryEntity?

    @Query("SELECT * FROM memories_table ORDER BY timestamp DESC")
    fun getAllMemoryEntries(): LiveData<List<MemoryEntity>>

    @Query("DELETE FROM memories_table")
    fun clear()
}