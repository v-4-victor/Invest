package com.v4victor.invest.di

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.v4victor.core.StockList
import com.v4victor.core.db.StocksRepository
import com.v4victor.invest.navigation.NavigateStocksImpl
import com.v4victor.invest.navigation.NavigateSearchImpl
import com.v4victor.search.NavigateSearch
import com.v4victor.stocks.NavigateStocks
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
        stocksRepository: StocksRepository,
        stockList: MutableLiveData<StockList>
    ): NavigateSearch {
        return NavigateSearchImpl(activity, stocksRepository, stockList)
    }

    @Provides
    fun provideNavigationToChart(
        activity: Activity
    ): NavigateStocks {
        return NavigateStocksImpl(activity)
    }

}