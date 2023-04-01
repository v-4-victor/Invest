package com.v4victor.invest.stockings

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navGraphViewModels
import com.v4victor.invest.R
import com.v4victor.invest.databinding.StocksFragmentBinding
import com.v4victor.network.StocksApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StockingsFragment : Fragment() {

    private val viewModel by viewModels<StockingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = StocksFragmentBinding.inflate(inflater)
        val recycler = binding.recycler
        val adapter = StockingsAdapter(StockingsAdapter.OnClickListener {

        })
        recycler.adapter = adapter
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel._stocksFlow.collect {
                    Log.d(TAG, it.toString())
                    adapter.submitList(it.map { item -> item.copy() })
                }
            }
        }
        return binding.root
    }

    val TAG = "StockingsFragment"

}