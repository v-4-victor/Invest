package com.v4victor.invest.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.v4victor.core.StockList
import com.v4victor.core.dto.CompanyProfile
import com.v4victor.core.dto.SearchInfo
import com.v4victor.core.dto.StringHolder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class ViewModelModule {
    @ActivityRetainedScoped
    @Provides
    fun provideStockList(): MutableLiveData<StockList> {
        return MutableLiveData(StockList())
    }
    @ActivityRetainedScoped
    @Provides
    fun provideCompanyProfile(
    ): StringHolder {
        return StringHolder()
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface BindsModule {
    @Binds
    fun bindLiveData(mutableLiveData: MutableLiveData<StockList>):LiveData<StockList>

}

