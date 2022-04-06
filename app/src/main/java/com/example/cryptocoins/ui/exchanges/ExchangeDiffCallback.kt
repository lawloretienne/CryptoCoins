package com.example.cryptocoins.ui.exchanges

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptocoins.domain.Exchange

class ExchangeDiffCallback : DiffUtil.ItemCallback<Exchange>() {
    override fun areItemsTheSame(oldItem: Exchange, newItem: Exchange): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Exchange, newItem: Exchange): Boolean {
        return oldItem == newItem
    }
}