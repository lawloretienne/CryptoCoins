package com.example.cryptocoins.ui.exchanges

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocoins.databinding.ExchangeRowBinding
import com.example.cryptocoins.domain.Exchange

class ExchangesAdapter : ListAdapter<Exchange, RecyclerView.ViewHolder>(ExchangeDiffCallback()) {

    var onItemClickListener: OnItemClickListener? = null

    private lateinit var binding: ExchangeRowBinding

    interface OnItemClickListener {
        fun onItemClick(exchange: Exchange)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ExchangeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = ExchangeViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val adapterPos = viewHolder.bindingAdapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) {
                onItemClickListener?.onItemClick(getItem(adapterPos))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as ExchangeViewHolder

        val exchange = getItem(position)
        holder.bind(exchange)
    }
}