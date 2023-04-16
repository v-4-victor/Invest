package com.v4victor.search

import com.v4victor.core.dto.SearchInfo

interface NavigateSearch {
    fun navigateToStocks(ticket: SearchInfo)
}