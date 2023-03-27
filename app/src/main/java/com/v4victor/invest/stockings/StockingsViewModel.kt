package com.v4victor.invest.stockings


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v4victor.core.StockList
import com.v4victor.core.dto.CompanyProfile
import com.v4victor.invest.db.Repository
import com.v4victor.websocket.Websocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StockingsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val stocksFlow: MutableSharedFlow<List<CompanyProfile>> =
        MutableSharedFlow(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val _stocksFlow: SharedFlow<List<CompanyProfile>> = stocksFlow

    private lateinit var companyList: List<CompanyProfile>

    val stocksList = StockList(
        listOf(
            CompanyProfile("AAPL", "APPLE", 100.0, 121.0),
            CompanyProfile("AMZN", "AMAZON", 100.0, 121.0),
            CompanyProfile("BINANCE:BTCUSDT", "BITCOIN", 100.0, 121.0),
        )
    )

    init {
        val TAG = "StockingsViewModel"
        Log.d(TAG, "Init first")
        viewModelScope.launch {
            repository.getCompanies().let { if (it.isNotEmpty()) companyList = it else stocksList }
            val ws = Websocket(stocksList._map.keys.toList(), viewModelScope).apply { start() }
            ws._flow.collect { list ->
                withContext(Dispatchers.Default)
                {
                    Log.d(TAG, "Thread: " + Thread.currentThread().name)
                    list.data.forEach { item ->
                        Log.d(TAG, "symbol ${item.symbol} price ${item.currentPrice}")
                        stocksList.updateStock(item.symbol, item.currentPrice)

                    }
                    stocksFlow.emit(stocksList._list)
                }
            }
        }
    }
}

