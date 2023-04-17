package com.v4victor.stocks.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.v4victor.stocks.databinding.NewsFragmentBinding

class NewsFragment : Fragment() {
    val company: String = "AAPL"
    val adapter = NewsAdapter()
    private lateinit var viewModel: NewsViewModel
    private lateinit var binder: NewsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = NewsFragmentBinding.inflate(inflater)
        binder.textError.visibility = View.INVISIBLE
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewModel.getMarsRealEstateProperties(company)
        binder.recyclerView.adapter = adapter
        viewModel.properties.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
        }
        viewModel.status.observe(viewLifecycleOwner)
        {
            when (it) {
                NewsViewModel.ApiStatus.DONE -> {
                    binder.progressBar.visibility = View.GONE
                    binder.textError.visibility = View.INVISIBLE
                }

                NewsViewModel.ApiStatus.ERROR -> {
                    binder.progressBar.visibility = View.GONE
                    binder.textError.visibility = View.VISIBLE
                    binder.textError.text = "Error"
                }

                else -> {
                    binder.textError.visibility = View.VISIBLE
                    binder.textError.text = "Loading"
                }
            }
        }
        return binder.root
    }

}