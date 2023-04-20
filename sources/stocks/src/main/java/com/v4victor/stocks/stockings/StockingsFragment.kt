package com.v4victor.stocks.stockings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.v4victor.core.StockList.SortOrder
import com.v4victor.core.dto.CompanyProfileHolder
import com.v4victor.core.updateValue
import com.v4victor.stocks.NavigateStocks
import com.v4victor.stocks.R
import com.v4victor.stocks.databinding.StocksFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StockingsFragment : Fragment() {
    @Inject
    lateinit var companyProfileHolder: CompanyProfileHolder

    @Inject
    lateinit var navigator: NavigateStocks

    private var sortOrder = SortOrder.ASCENDING
    private val viewModel by viewModels<StockingsViewModel>()
    private var favourite = false
    private lateinit var mIth: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = StocksFragmentBinding.inflate(inflater)
        val recycler = binding.recycler
        val adapter = StockingsAdapter(StockingsAdapter.OnClickListener {
            companyProfileHolder.companyProfile = viewModel.getCompanyProfile(it.symbol)!!
            navigator.navigateToChart()
        })
        recycler.adapter = adapter

        mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewHolder as StockingsAdapter.ViewHolder
                    viewModel.deleteTicker(viewHolder.binding.symbol.text.toString())
                }
            })
        mIth.attachToRecyclerView(recycler)

        viewModel.stocksLiveList.observe(viewLifecycleOwner)
        {
            Log.d(TAG, it._list.toString())
            if (!favourite)
                adapter.submitList(it._list.map { item -> item.copy() })
            else
                adapter.submitList(it._list.filter { item -> item.favourite }
                    .map { item -> item.copy() })

        }
        viewModel.updateStocks()

        binding.materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.searchStock -> {
                    navigator.navigateToSearch()
                    true
                }

                R.id.favourite -> {
                    favourite = !favourite
                    val icon =
                        if (favourite) R.drawable.star_sort else R.drawable.star_no_item
                    it.setIcon(icon)
                    viewModel.stocksLiveList.updateValue()
                    true
                }

                R.id.sort -> {
                    sortOrder =
                        when (sortOrder) {
                            SortOrder.ASCENDING -> SortOrder.DESCENDING
                            SortOrder.DESCENDING -> SortOrder.ASCENDING
                            SortOrder.DATE -> SortOrder.DATE
                        }
                    viewModel.stocksLiveList.value?.changeSortOrder(sortOrder)
                    viewModel.stocksLiveList.updateValue()
                    true
                }

                else -> false
            }


        }
        return binding.root
    }

    val TAG = "StockingsFragment"

}