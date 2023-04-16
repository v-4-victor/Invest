package com.v4victor.invest.navigation

import android.app.Activity
import androidx.navigation.findNavController
import com.v4victor.invest.R
import com.v4victor.stocks.NavigateStocks
import javax.inject.Inject

class NavigateStocksImpl @Inject constructor(
    private val activity: Activity
) : NavigateStocks {
    override fun navigateToChart() {
        activity.findNavController(R.id.nav_host_fragment)
            .navigate(R.id.action_stockingsFragment_to_chartFragment)
    }

    override fun navigateToSearch() {
        activity.findNavController(R.id.nav_host_fragment)
            .navigate(R.id.action_stockingsFragment_to_searchFragment)
    }
}
