package com.v4victor.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.v4victor.search.databinding.SearchLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: SearchViewModel
    private lateinit var binder: SearchLayoutBinding

    @Inject
    lateinit var navigator: NavigateSearch
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = SearchLayoutBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
//        viewModel.getTickets("")
        adapter = SearchAdapter(navigator)
        binder.recyclerTickers.adapter = adapter

        viewModel.companies.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
            binder.recyclerTickers.scrollToPosition(0)
        }
        viewModel.status.observe(viewLifecycleOwner)
        {
            if (it == SearchViewModel.ApiStatus.LOADING) {
                binder.progressBar.visibility = View.VISIBLE
                binder.recyclerTickers.visibility = View.INVISIBLE
            }
            if (it == SearchViewModel.ApiStatus.DONE) {
                binder.progressBar.visibility = View.INVISIBLE
                binder.recyclerTickers.visibility = View.VISIBLE
            }
            if (it == SearchViewModel.ApiStatus.ERROR) {
                binder.progressBar.visibility = View.INVISIBLE
                binder.recyclerTickers.visibility = View.INVISIBLE
            }
        }
        binder.editTextTextPersonName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

//        binder.editTextTextPersonName.setOnKeyListener { _, keyCode, event ->
//            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
//                updateRepoListFromInput()
//                true
//            } else {
//                false
//            }
//        }
        binder.floatingActionButton.setOnClickListener {
            updateRepoListFromInput()

        }
        return binder.root
    }

    private fun updateRepoListFromInput() {
        binder.editTextTextPersonName.text.trim().let {
            if (it.isNotEmpty()) {
                binder.recyclerTickers.scrollToPosition(0)
                viewModel.getTickets(it.toString())
            }
        }
    }

}