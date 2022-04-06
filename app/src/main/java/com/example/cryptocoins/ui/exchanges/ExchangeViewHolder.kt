package com.example.cryptocoins.ui.exchanges

import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocoins.databinding.ExchangeRowBinding
import com.example.cryptocoins.domain.Exchange
import com.squareup.picasso.Picasso

class ExchangeViewHolder(binding: ExchangeRowBinding) : RecyclerView.ViewHolder(binding.root) {

    private val nameTextView = binding.nameTextView
    private val imageView = binding.imageView

    fun bind(exchange: Exchange) {
        nameTextView.text = exchange.name

        Picasso.get()
            .load(exchange.image)
            .into(imageView)
    }
}