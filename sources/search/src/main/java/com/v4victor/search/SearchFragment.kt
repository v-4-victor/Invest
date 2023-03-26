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


class SearchFragment : Fragment() {

    val adapter = SearchAdapter()
    private lateinit var viewModel: SearchViewModel
    private lateinit var binder: SearchLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = SearchLayoutBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.getMarsRealEstateProperties("")
        binder.recyclerTickers.adapter = adapter

        viewModel.properties.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
            binder.recyclerTickers.scrollToPosition(0)
        }

        binder.editTextTextPersonName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        binder.editTextTextPersonName.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        return binder.root
    }

    private fun updateRepoListFromInput() {
        binder.editTextTextPersonName.text.trim().let {
            if (it.isNotEmpty()) {
                binder.recyclerTickers.scrollToPosition(0)
                viewModel.getMarsRealEstateProperties(it.toString())
            }
        }
    }

//    fun Fragment.hideKeyboard() {
//        view?.let { activity?.hideKeyboard(it) }
//    }
//
//    fun Context.hideKeyboard(view: View) {
//        val inputMethodManager =
//            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//    }
}