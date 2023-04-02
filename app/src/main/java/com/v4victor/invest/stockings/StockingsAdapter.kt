package com.v4victor.invest.stockings

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.v4victor.core.dto.CompanyProfile
import com.v4victor.invest.R
import com.v4victor.invest.databinding.ListItemBinding

class StockingsAdapter(
    private val onClickListener: OnClickListener
) : ListAdapter<CompanyProfile, RecyclerView.ViewHolder>(REPO_COMPARATOR) {
    class ViewHolder private constructor(
         val binding: ListItemBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CompanyProfile) {
            val url = "https://finnhub.io/api/logo?symbol=${item.symbol}"
            Glide.with(context)
                .load(url)
                .error(R.drawable.ic_broken)
                .into(binding.imageView)
            binding.symbol.text = item.symbol
            binding.currentPrice.text = item.currentPrice.toString()
            binding.description.text = item.description
            binding.openPrice.text = item.openPrice.toString()
        }

        fun updatePrice(price: String) {
            binding.currentPrice.text = price
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, parent.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] is Double) {
                (holder as ViewHolder).updatePrice(payloads[0].toString())
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(repoItem)
        }
        if (repoItem != null) {
            (holder as ViewHolder).bind(repoItem)
        }
    }

    companion object {

        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<CompanyProfile>() {
            override fun areItemsTheSame(oldItem: CompanyProfile, newItem: CompanyProfile): Boolean {
                return oldItem.symbol == newItem.symbol
            }

            override fun areContentsTheSame(oldItem: CompanyProfile, newItem: CompanyProfile): Boolean {
                return oldItem.currentPrice == newItem.currentPrice
            }

            override fun getChangePayload(oldItem: CompanyProfile, newItem: CompanyProfile): Any {
                return newItem.currentPrice
            }
        }
    }

    class OnClickListener(val clickListener: (stock: CompanyProfile) -> Unit) {
        fun onClick(stock: CompanyProfile) = clickListener(stock)
    }

}
