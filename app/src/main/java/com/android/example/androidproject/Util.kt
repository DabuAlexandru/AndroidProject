package com.android.example.androidproject

import android.text.TextUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

public fun validateForm(reqEmail : android.widget.EditText, reqPass : android.widget.EditText): Boolean {
    var valid = true

    val email = reqEmail.text.toString()
    if (TextUtils.isEmpty(email)) {
        reqEmail.error = "Required."
        valid = false
    } else {
        reqEmail.error = null
    }

    val password = reqPass.text.toString()
    if (TextUtils.isEmpty(password)) {
        reqPass.error = "Required."
        valid = false
    } else {
        reqPass.error = null
    }

    return valid
}