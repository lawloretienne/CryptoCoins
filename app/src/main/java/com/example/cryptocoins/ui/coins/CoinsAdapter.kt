package com.example.cryptocoins.ui.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocoins.R
import com.example.cryptocoins.domain.Coin

class CoinsAdapter : ListAdapter<Coin, RecyclerView.ViewHolder>(CoinDiffCallback()) {

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(coin: Coin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_row, parent, false)
        val viewHolder = CoinViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val adapterPos = viewHolder.adapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) {
                onItemClickListener?.onItemClick(getItem(adapterPos))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as CoinViewHolder

        val coin = getItem(position)
        holder.bind(coin)
    }
}