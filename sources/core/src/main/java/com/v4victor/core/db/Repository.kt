package com.v4victor.core.db

import com.v4victor.core.dto.CompanyProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Repository(database: CompanyDB) {

    private val dao = database.companyDao

    suspend fun insertAll(listCompany: List<CompanyProfile>) {
        withContext(Dispatchers.IO) {
            dao.insertAll(listCompany)
        }
    }
    suspend fun insert(company: CompanyProfile) {
        withContext(Dispatchers.IO) {
            dao.insert(company)
        }
    }
    suspend fun delete(symbol: String) {
        withContext(Dispatchers.IO) {
            dao.clear(symbol)
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO)
        {
            dao.clear()
        }
    }

    suspend fun getCompanies() = dao.getCompanies()

}
