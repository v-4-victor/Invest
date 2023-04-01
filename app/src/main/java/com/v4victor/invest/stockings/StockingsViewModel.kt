package com.v4victor.invest.stockings


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.v4victor.core.StockList
import com.v4victor.core.dto.CompanyProfile
import com.v4victor.core.db.Repository
import com.v4victor.core.dto.SearchInfo
import com.v4victor.core.updateValue
import com.v4victor.network.StocksApi
import com.v4victor.websocket.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class StockingsViewModel @Inject constructor(
    private val repository: Repository,
//    private val stockList: StockList
    val stocksLiveList: MutableLiveData<StockList>
) : ViewModel() {
    private val stocksFlow: MutableSharedFlow<List<CompanyProfile>> =
        MutableSharedFlow(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val _stocksFlow: SharedFlow<List<CompanyProfile>> = stocksFlow

//    private lateinit var stocksLiveList: MutableLiveData<StockList>
//    val _stocksList: LiveData<StockList> = stocksLiveList

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
                val ws =
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
//                            stocksFlow.emit(stockList._list)
                    }
                }
            }


//            stocksList = ws._liveData.map {list ->
//                list.data.forEach { item ->
//                        Log.d(TAG, "symbol ${item.symbol} price ${item.currentPrice}")
//                        stockList.updateStock(item.symbol, item.currentPrice)
//                    }
//                list
//            }
//            ws._flow.collect { list ->
//                withContext(Dispatchers.Default)
//                {
//                    Log.d(TAG, "Thread: " + Thread.currentThread().name)
//                    Log.d(TAG, stockList._list.toString())
//                    list.data.forEach { item ->
//                        Log.d(TAG, "symbol ${item.symbol} price ${item.currentPrice}")
//                        stockList.updateStock(item.symbol, item.currentPrice)
//                    }
//                    stocksFlow.emit(stockList._list)
//                }
//            }
        }
    }

}

