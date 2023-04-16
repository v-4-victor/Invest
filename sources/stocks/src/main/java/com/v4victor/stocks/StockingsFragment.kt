package com.v4victor.stocks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.v4victor.core.dto.CompanyProfile
import com.v4victor.core.dto.CompanyProfileHolder
import com.v4victor.core.dto.StringHolder
import com.v4victor.stocks.databinding.StocksFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StockingsFragment : Fragment() {
    @Inject
    lateinit var companyProfileHolder: CompanyProfileHolder
    @Inject
    lateinit var navigator: NavigateToChartFragment

    private val viewModel by viewModels<StockingsViewModel>()
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
            navigator.navigate()
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
            adapter.submitList(it._list.map { item -> item.copy() })
        }
        viewModel.updateStocks()
        return binding.root
    }

    val TAG = "StockingsFragment"

}