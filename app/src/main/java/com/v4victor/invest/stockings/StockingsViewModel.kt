package com.v4victor.invest.stockings


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v4victor.core.StockList
import com.v4victor.core.db.Repository
import com.v4victor.core.dto.CompanyProfile
import com.v4victor.core.updateValue
import com.v4victor.websocket.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StockingsViewModel @Inject constructor(
    private val repository: Repository,
    val stocksLiveList: MutableLiveData<StockList>
) : ViewModel() {
    private lateinit var ws: WebSocketClient

    val defaultStocksList =
        listOf(
            CompanyProfile("AAPL", "APPLE", 100.0, 121.0),
            CompanyProfile("AMZN", "AMAZON", 100.0, 121.0),
            CompanyProfile("BINANCE:BTCUSDT", "BITCOIN", 100.0, 121.0),
        )


    init {
        val TAG = "StockingsViewModel"
        Log.d(TAG, "Init first")
        viewModelScope.launch {

            stocksLiveList.value?.let { stockList ->
                if (stockList.isEmpty())
                    repository.getCompanies()
                        .let {
                            stockList.addAll(it.ifEmpty {
                                withContext(Dispatchers.Default)
                                {
                                    repository.insertAll(defaultStocksList)
                                }
                                defaultStocksList
                            })
                        }
                ws =
                    WebSocketClient(
                        stockList._map.keys.toList(),
                        viewModelScope
                    ).apply { start() }
                ws._flow.collect { list ->
                    withContext(Dispatchers.Default)
                    {
                        Log.d(TAG, "Thread: " + Thread.currentThread().name)
                        Log.d(TAG, stockList._list.toString())
                        list.data.forEach { item ->
                            Log.d(TAG, "symbol ${item.symbol} price ${item.currentPrice}")
                            stockList.updateStock(item.symbol, item.currentPrice)
                        }
                        withContext(Dispatchers.Main)
                        {
                            stocksLiveList.updateValue()
                        }
                    }
                }
            }
        }
    }

    fun deleteTicker(symbol: String) {
        viewModelScope.launch {
            repository.delete(symbol)
            stocksLiveList.value?.minusAssign(symbol)
            stocksLiveList.updateValue()
        }

    }

}

