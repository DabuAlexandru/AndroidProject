package com.android.example.androidproject.memories

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.android.example.androidproject.R
import com.android.example.androidproject.database.MemoryEntity

class MemoryEntityAdapter(var memoriesList: List<MemoryEntity>) : RecyclerView.Adapter<MemoryEntityAdapter.ViewHolder>() {

    override fun getItemCount() = memoriesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = memoriesList[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val memoryImage: ImageView = itemView.findViewById(R.id.memory_image)
        val memoryTimestamp: TextView = itemView.findViewById(R.id.memory_timestamp)
        val memoryDescription: TextView = itemView.findViewById(R.id.memory_description)

        fun bind(item: MemoryEntity) {
            memoryImage.setImageURI(item.imageURI.toUri())
            memoryDescription.text = item.description
            memoryTimestamp.text = DateFormat.format("dd-MM-yyyy HH:mm", item.timestamp).toString()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_memory_entity, parent, false)
                return ViewHolder(view)
            }
        }
    }

}