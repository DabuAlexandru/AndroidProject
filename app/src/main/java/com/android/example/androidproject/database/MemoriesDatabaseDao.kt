package com.android.example.androidproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MemoriesDatabaseDao {
    @Insert
    suspend fun insert(night: MemoryEntity)

    @Update
    suspend fun update(night: MemoryEntity)

    @Query("SELECT * from memories_table WHERE memoryId = :key")
    suspend fun get(key: Long): MemoryEntity?

    @Query("SELECT * FROM memories_table ORDER BY timestamp DESC")
    fun getAllMemoryEntries(): LiveData<List<MemoryEntity>>

    @Query("DELETE FROM memories_table")
    suspend fun clear()
}