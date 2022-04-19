package com.android.example.androidproject.database

import android.text.format.DateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "memories_table")
data class MemoryEntity(
    @PrimaryKey(autoGenerate = true)
    val memoryId: Long = 0L,

    @ColumnInfo(name="description")
    val description: String = "(picture took at " + DateFormat.format("dd-MM-yyyy HH:mm:ss", System.currentTimeMillis()).toString() + ")",

    @ColumnInfo(name="imageURI")
    var imageURI: String = "",

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),
)