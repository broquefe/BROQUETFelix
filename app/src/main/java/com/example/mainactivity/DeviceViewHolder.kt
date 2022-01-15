package com.example.mainactivity

import com.afollestad.recyclical.ViewHolder
import android.view.View
import android.widget.TextView

class DeviceViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.title)
}