package com.v4victor.core

import com.v4victor.core.dto.CompanyProfile
import com.v4victor.core.dto.PriceInfo
import com.v4victor.core.dto.SearchInfo
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StockListTest {

    @Test
    fun stockListCorrect() {
        val stockList = StockList()
        val list =
            mutableListOf(
                CompanyProfile(SearchInfo("AAPL", "AAPL", "AAPL"))
                        + PriceInfo(100.0, 200.0),
                CompanyProfile(SearchInfo("MSFT", "MSFT", "MSFT"))
                        + PriceInfo(100.0, 200.0),
                CompanyProfile(SearchInfo("BITCOIN", "BITCOIN", "BITCOIN"))
                        + PriceInfo(100.0, 200.0)
            )
        list.forEach { stockList += it }
        assertEquals(stockList["MSFT"], list[1])
        stockList.changeSortOrder(StockList.SortOrder.ASCENDING)
        assertEquals(stockList["MSFT"], list[1])
        stockList -= "MSFT"
        assertEquals(stockList["MSFT"], null)
        assertEquals(stockList._list.size, 2)
        list.removeAt(1)
        assertArrayEquals(
            list.sortedBy { it.symbol }.toTypedArray(),
            stockList._list.toTypedArray()
        )

    }
}