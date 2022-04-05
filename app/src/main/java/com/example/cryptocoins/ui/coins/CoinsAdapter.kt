package com.example.cryptocoins.ui.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocoins.R
import com.example.cryptocoins.databinding.ActivityCoinDetailsBinding
import com.example.cryptocoins.databinding.CoinRowBinding
import com.example.cryptocoins.domain.Coin

class CoinsAdapter : ListAdapter<Coin, RecyclerView.ViewHolder>(CoinDiffCallback()) {

    var onItemClickListener: OnItemClickListener? = null

    private lateinit var binding: CoinRowBinding

    interface OnItemClickListener {
        fun onItemClick(coin: Coin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = CoinRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = CoinViewHolder(binding)
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