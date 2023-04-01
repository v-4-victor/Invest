package com.v4victor.invest.stockings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.v4victor.core.dto.StringHolder
import com.v4victor.invest.R
import com.v4victor.invest.databinding.StocksFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StockingsFragment : Fragment() {
    @Inject
    lateinit var stringHolder: StringHolder
    private val viewModel by viewModels<StockingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = StocksFragmentBinding.inflate(inflater)
        val recycler = binding.recycler
        val adapter = StockingsAdapter(StockingsAdapter.OnClickListener {
            stringHolder.data = it.symbol
            findNavController().navigate(R.id.action_stockingsFragment_to_chartFragment)
        })
        recycler.adapter = adapter
//        lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
//            {
//                viewModel._stocksFlow.collect {
//                    Log.d(TAG, it.toString())
//                    adapter.submitList(it.map { item -> item.copy() })
//                }
//            }
//        }
        viewModel.stocksLiveList.observe(viewLifecycleOwner)
        {
            Log.d(TAG, it.toString())
            adapter.submitList(it._list.map { item -> item.copy() })
        }
        return binding.root
    }

    val TAG = "StockingsFragment"

}