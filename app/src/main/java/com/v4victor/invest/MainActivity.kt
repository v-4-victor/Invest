package com.v4victor.invest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import com.v4victor.invest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHost: FragmentContainerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        navHost = binding.navHostFragment
        setContentView(view)
    }

//    override fun navigate(ticket: SearchInfo) {
//        findNavController(R.id.nav_host_fragment)
//        navHost.findNavController().navigate(R.id.action_searchFragment_to_stockingsFragment)
//    }
}