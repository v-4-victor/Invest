package com.v4victor.invest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.v4victor.invest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        binding.materialToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.search) {
                binding.navHostFragment.findNavController()
                    .navigate(R.id.action_stockingsFragment_to_searchFragment)
                return@setOnMenuItemClickListener true
            }
            false
        }
        setContentView(view)
    }
}