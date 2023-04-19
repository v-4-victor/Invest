package com.v4victor.invest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.elevation.SurfaceColors
import com.v4victor.invest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHost: FragmentContainerView
    private var iconFocus = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        navHost = binding.navHostFragment
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        val color = SurfaceColors.SURFACE_2.getColor(this)
        window.statusBarColor = color // Set color of system statusBar same as ActionBar
        window.navigationBarColor = color // Set color of system navigationBar same as BottomNavigationView
//        binding.bottomNavigationView.set
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.stocks_list -> {
//                    iconFocus = !iconFocus
//                    true
//                }
//
//                R.id.news_list -> {
//                    iconFocus = !iconFocus
//                    val icon =
//                        if (iconFocus) R.drawable.star_list_focus else R.drawable.star_list_no_focus
//                    it.setIcon(icon)
//                    true
//                }
//
//                else -> false
//            }
//        }
    }


//    override fun navigate(ticket: SearchInfo) {
//        findNavController(R.id.nav_host_fragment)
//        navHost.findNavController().navigate(R.id.action_searchFragment_to_stockingsFragment)
//    }
}