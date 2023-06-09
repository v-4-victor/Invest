package com.v4victor.core

import android.util.Log
import com.v4victor.core.dto.CompanyProfile

class StockList(private val list: MutableList<CompanyProfile> = mutableListOf()) {
    private val map = mutableMapOf<String, Int>()
    var sortOrder = SortOrder.ASCENDING
        private set

    val _list: List<CompanyProfile> = list
    val _map: Map<String, Int> = map

    operator fun minusAssign(symbol: String) {
        map[symbol]?.let { list.removeAt(it) }
        map -= symbol
        sortCompanyProfileList()
    }

    fun addAll(CompanyProfiles: List<CompanyProfile>) {
        list.addAll(CompanyProfiles)
        sortCompanyProfileList()
    }

    fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    operator fun plusAssign(companyProfile: CompanyProfile) {
        if (map[companyProfile.symbol] == null) {
            list.add(companyProfile)
            sortCompanyProfileList()
        }
    }

    fun updateStock(ticket: String, price: Double) {
//        Log.d(TAG, "Thread: " + Thread.currentThread().name)
        if (map[ticket] != null)
            list[map[ticket]!!].currentPrice = price
    }
    operator fun get(symbol: String) = if (map[symbol] != null)
        list[map[symbol]!!] else null
    fun getProfile(symbol: String) = if (map[symbol] != null)
        list[map[symbol]!!] else null

    fun changeSortOrder(order: SortOrder) {
        sortOrder = order
        sortCompanyProfileList()
    }

    private fun sortCompanyProfileList() {
        when (sortOrder) {
            SortOrder.ASCENDING -> list.sortBy { it.symbol }
            SortOrder.DESCENDING -> list.sortByDescending { it.symbol }
            SortOrder.DATE -> {}
        }
        list.forEachIndexed { i, CompanyProfile ->
            map[CompanyProfile.symbol] = i
        }
    }

    enum class SortOrder {
        ASCENDING,
        DESCENDING,
        DATE
    }

    val TAG = "CompanyProfileList"
}