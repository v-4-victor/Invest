package com.v4victor.invest.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.v4victor.core.StockList
import com.v4victor.core.db.Repository
import com.v4victor.core.dto.CompanyProfile
import com.v4victor.invest.MainActivity
import com.v4victor.invest.R
import com.v4victor.invest.navigation.NavigateToStocksFragmentImpl
import com.v4victor.search.NavigateToStocksFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun provideNavigation(
        activity: Activity,
        repository: Repository,
        stockList: MutableLiveData<StockList>
    ): NavigateToStocksFragment {
        return NavigateToStocksFragmentImpl(activity, repository, stockList)
    }

}