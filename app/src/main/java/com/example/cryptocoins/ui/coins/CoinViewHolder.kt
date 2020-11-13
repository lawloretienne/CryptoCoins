package com.example.cryptocoins.ui.coins

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocoins.domain.Coin
import kotlinx.android.synthetic.main.coin_row.view.*

class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameTextView = itemView.nameTextView
    private val symbolTextView = itemView.symbolTextView

    fun bind(coin: Coin) {
        nameTextView.text = coin.name
        symbolTextView.text = coin.symbol
    }
}