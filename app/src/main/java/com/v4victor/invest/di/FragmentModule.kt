package com.v4victor.invest.di

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
    fun provideNavigation():NavigateToStocksFragment{
        return NavigateToStocksFragmentImpl()
    }
}