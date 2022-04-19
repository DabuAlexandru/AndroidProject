package com.android.example.androidproject.memories

import android.app.Application
import android.os.Build
import android.text.Html
import androidx.core.text.HtmlCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.android.example.androidproject.database.MemoriesDatabaseDao
import com.android.example.androidproject.database.MemoryEntity
import kotlinx.coroutines.launch

class MemoriesViewModel(
    val database: MemoriesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val memories = database.getAllMemoryEntries()

    val memoriesString = Transformations.map(memories) { memory ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(memory.toString(), Html.FROM_HTML_MODE_LEGACY)
        } else {
            HtmlCompat.fromHtml(memory.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    private suspend fun insert(memory: MemoryEntity) {
        database.insert(memory)
    }

    private suspend fun update(memory: MemoryEntity) {
        database.update(memory)
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
        }
    }

    suspend fun clear() {
        database.clear()
    }
}