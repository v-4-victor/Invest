package com.v4victor.invest.navigation

import android.app.Activity
import androidx.navigation.findNavController
import com.v4victor.invest.R
import com.v4victor.stocks.NavigateToChartFragment
import javax.inject.Inject

class NavigateToChartFragmentImpl @Inject constructor(
    private val activity: Activity
) :
    NavigateToChartFragment {
    override fun navigate() {
        activity.findNavController(R.id.nav_host_fragment)
            .navigate(R.id.action_stockingsFragment_to_chartFragment)
    }
}