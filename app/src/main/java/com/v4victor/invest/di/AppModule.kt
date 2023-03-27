package com.v4victor.invest.di

import android.content.Context
import com.v4victor.invest.db.Repository
import com.v4victor.invest.db.getDatabase
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
    fun provideDB(@ApplicationContext context: Context): Repository {
       return Repository(getDatabase(context))
    }
}
