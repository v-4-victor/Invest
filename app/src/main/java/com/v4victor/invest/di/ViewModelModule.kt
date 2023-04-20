package com.v4victor.invest.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.v4victor.core.StockList
import com.v4victor.core.dto.CompanyProfileHolder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

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
    ): CompanyProfileHolder {
        return CompanyProfileHolder()
    }
    @Provides
    fun provideDispatcher(): CoroutineContext = Dispatchers.Default
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface BindsModule {
    @Binds
    fun bindLiveData(mutableLiveData: MutableLiveData<StockList>):LiveData<StockList>

}

