package com.v4victor.invest.db

import com.v4victor.core.dto.CompanyProfile
import com.v4victor.invest.db.CompanyDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Repository(database: CompanyDB) {

    private val dao = database.companyDao

    suspend fun insertAll(listCompany: List<CompanyProfile>) {
        withContext(Dispatchers.IO) {
            dao.insertAll(listCompany)
        }
    }

    suspend fun delete(set: String) {
        withContext(Dispatchers.IO) {
            dao.clear(set)
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
