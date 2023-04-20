package com.v4victor.core.db

import android.content.Context
import androidx.room.*
import com.v4victor.core.dto.CompanyProfile

@Dao
interface CompanyDao {
    @Query("select * from CompanyProfile")
    suspend fun getCompanies(): List<CompanyProfile>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(companies: List<CompanyProfile>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(company: CompanyProfile)

    @Query("delete from CompanyProfile where symbol = :symbol")
    fun clear(symbol: String)

    @Query("delete from CompanyProfile")
    fun clear()

    @Update
    fun updateAll(profiles: List<CompanyProfile>)
}


@Database(entities = [CompanyProfile::class], version = 4)
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