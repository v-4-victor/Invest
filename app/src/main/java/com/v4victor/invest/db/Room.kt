package com.v4victor.invest.db

import android.content.Context
import androidx.room.*
import com.v4victor.core.dto.CompanyProfile

@Dao
interface CompanyDao {
    @Query("select * from CompanyProfile")
    suspend fun getCompanies(): List<CompanyProfile>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(companies: List<CompanyProfile>)

    @Query("delete from CompanyProfile where symbol = :symbol")
    fun clear(symbol: String)
    @Query("delete from CompanyProfile")
    fun clear()
}


@Database(entities = [CompanyProfile::class], version = 1)
abstract class CompanyDB : RoomDatabase() {
    abstract val companyDao: CompanyDao
}

private lateinit var INSTANCE: CompanyDB

fun getDatabase(context: Context): CompanyDB {
    synchronized(CompanyDB::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CompanyDB::class.java,
                "companies.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}