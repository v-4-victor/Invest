package com.v4victor.invest.di

import android.content.Context
import com.v4victor.core.StockList
import com.v4victor.core.db.Repository
import com.v4victor.core.db.getDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideStockList(): StockList
    {
        return StockList()
    }
    @Singleton
    @Provides
    fun provideRepository(@ApplicationContext context: Context): Repository {
       return Repository(getDatabase(context))
    }
}
