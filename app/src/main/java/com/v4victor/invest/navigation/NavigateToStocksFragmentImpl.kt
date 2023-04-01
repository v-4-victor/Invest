package com.v4victor.invest.navigation

import android.app.Activity
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.v4victor.core.StockList
import com.v4victor.core.db.Repository
import com.v4victor.core.dto.CompanyProfile
import com.v4victor.core.dto.SearchInfo
import com.v4victor.core.updateValue
import com.v4victor.invest.MainActivity
import com.v4victor.invest.R
import com.v4victor.network.StocksApi
import com.v4victor.search.NavigateToStocksFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NavigateToStocksFragmentImpl @Inject constructor(
    private val activity: Activity,
    private val repository: Repository,
    private val companyList: MutableLiveData<StockList>
) : NavigateToStocksFragment {
    override fun navigate(ticket: SearchInfo) {
        activity as AppCompatActivity
        activity.findNavController(R.id.nav_host_fragment)
            .navigate(R.id.action_searchFragment_to_stockingsFragment)
        activity.lifecycleScope.launch {
            withContext(Dispatchers.Default)
            {
                val stock = CompanyProfile(ticket)
                stock += StocksApi.retrofitService.getPrice(ticket.symbol)
                launch(Dispatchers.IO) {
                    repository.insert(stock)
                }
                withContext(Dispatchers.Main)
                {
                    companyList.value?.plusAssign(stock)
                    companyList.updateValue()
                }
            }
        }
    }
}