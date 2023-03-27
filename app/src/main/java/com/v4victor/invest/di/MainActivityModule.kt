package com.v4victor.invest.di

import android.util.Log
import com.v4victor.core.dto.CompanyProfile
import com.v4victor.invest.db.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.runBlocking

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {
    @ViewModelScoped
    @Provides
    fun getCompanyList(repository: Repository): List<CompanyProfile> {
        Log.d(TAG, "Hilt first")
        return runBlocking { repository.getCompanies() }
    }
}

val TAG = "MainActivityModule"