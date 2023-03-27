package com.v4victor.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.v4victor.core.dto.SearchInfo
import com.v4victor.search.databinding.SearchItemBinding

class SearchAdapter(private val navigator: NavigateToStocksFragment) :
    ListAdapter<SearchInfo, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        holder.itemView.setOnClickListener {
            navigator.navigate(repoItem)
        }

        if (repoItem != null) {
            (holder as ViewHolder).bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<SearchInfo>() {
            override fun areItemsTheSame(oldItem: SearchInfo, newItem: SearchInfo): Boolean =
                oldItem.companyName == newItem.companyName

            override fun areContentsTheSame(oldItem: SearchInfo, newItem: SearchInfo): Boolean =
                oldItem.companyName == newItem.companyName && oldItem.description == newItem.description
        }
    }
}


class ViewHolder private constructor(
    private val binding: SearchItemBinding,
    val context: Context
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SearchInfo) {
        val url = "https://finnhub.io/api/logo?symbol=${item.symbol}"
        Glide.with(context)
            .load(url)
            .error(R.drawable.ic_broken)
            .into(binding.imageView)
        binding.symbol.text = item.symbol

    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                SearchItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding, parent.context)
        }
    }

}