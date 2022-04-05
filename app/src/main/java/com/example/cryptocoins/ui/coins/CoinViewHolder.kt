package com.example.cryptocoins.ui.coins

import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocoins.databinding.CoinRowBinding
import com.example.cryptocoins.domain.Coin

class CoinViewHolder(binding: CoinRowBinding) : RecyclerView.ViewHolder(binding.root) {

    private val nameTextView = binding.nameTextView
    private val symbolTextView = binding.symbolTextView

    fun bind(coin: Coin) {
        nameTextView.text = coin.name
        symbolTextView.text = coin.symbol
    }
}