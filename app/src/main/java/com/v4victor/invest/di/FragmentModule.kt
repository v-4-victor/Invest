package com.v4victor.invest.di

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.v4victor.core.StockList
import com.v4victor.core.db.Repository
import com.v4victor.invest.navigation.NavigateToChartFragmentImpl
import com.v4victor.invest.navigation.NavigateToStocksFragmentImpl
import com.v4victor.search.NavigateToStocksFragment
import com.v4victor.stocks.NavigateToChartFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun provideNavigationToStocks(
        activity: Activity,
        repository: Repository,
        stockList: MutableLiveData<StockList>
    ): NavigateToStocksFragment {
        return NavigateToStocksFragmentImpl(activity, repository, stockList)
    }

    @Provides
    fun provideNavigationToChart(
        activity: Activity
    ): NavigateToChartFragment {
        return NavigateToChartFragmentImpl(activity)
    }
}