package com.example.cryptocoins.ui.exchanges

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.cryptocoins.R
import com.example.cryptocoins.databinding.ExchangeRowBinding
import com.example.cryptocoins.domain.Exchange

class ExchangeViewHolder(binding: ExchangeRowBinding) : RecyclerView.ViewHolder(binding.root) {

    private val nameTextView = binding.nameTextView
    private val imageView = binding.imageView

    fun bind(exchange: Exchange) {
        nameTextView.text = exchange.name

        imageView.load(exchange.image) {
            transformations(CircleCropTransformation())
            placeholder(R.drawable.photo_placeholder)
            error(R.drawable.photo_error)
        }
    }
}