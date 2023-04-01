package com.v4victor.invest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import com.v4victor.core.dto.SearchInfo
import com.v4victor.invest.databinding.ActivityMainBinding
import com.v4victor.search.NavigateToStocksFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHost: FragmentContainerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        navHost = binding.navHostFragment
        binding.materialToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.search) {
                navHost.findNavController()
                    .navigate(R.id.action_stockingsFragment_to_searchFragment)
                return@setOnMenuItemClickListener true
            }
            false
        }
        setContentView(view)
    }

//    override fun navigate(ticket: SearchInfo) {
//        findNavController(R.id.nav_host_fragment)
//        navHost.findNavController().navigate(R.id.action_searchFragment_to_stockingsFragment)
//    }
}