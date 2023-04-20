package com.v4victor.stocks.stockings


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v4victor.core.StockList
import com.v4victor.core.db.StocksRepository
import com.v4victor.core.dto.CompanyProfile
import com.v4victor.core.updateValue
import com.v4victor.websocket.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class StockingsViewModel @Inject constructor(
    private val stocksRepository: StocksRepository,
    val stocksLiveList: MutableLiveData<StockList>,
    private val dispatcher: CoroutineContext
) : ViewModel() {
    private val TAG = "StockingsViewModel"

    val defaultStocksList =
        listOf(
            CompanyProfile("AAPL", "APPLE", 100.0, 121.0),
            CompanyProfile("AMZN", "AMAZON", 100.0, 121.0),
            CompanyProfile("BINANCE:BTCUSDT", "BITCOIN", 100.0, 121.0),
        )


    init {
        Log.d(TAG, "Init first")
        viewModelScope.launch {

            val stockList = stocksLiveList.value ?: return@launch
            if (stockList.isEmpty()) {
                stocksRepository.getCompanies().let { repositoryList ->
                    if (repositoryList.isEmpty()) {
                        stockList.addAll(defaultStocksList)
                        stocksRepository.insertAll(defaultStocksList)
                    } else {
                        stockList.addAll(repositoryList)
                    }
                }
            } else {
                Log.d(TAG, "List not Empty ${stockList._list}")
            }

            val ws = WebSocketClient(
                stockList._map.keys.toList(),
                viewModelScope
            ).apply { start() }

            ws._flow
                .onEach { listStock ->
                    Log.d(TAG, "Thread: " + Thread.currentThread().name)
                    Log.d(TAG, stockList._list.toString())

                    listStock.data.forEach { item ->
                        Log.d(TAG, "symbol ${item.symbol} price ${item.currentPrice}")
                        stockList.updateStock(item.symbol, item.currentPrice)
                    }
                }
                .flowOn(dispatcher)
                .onEach { stocksLiveList.updateValue() }
                .launchIn(viewModelScope)
        }
    }

    fun deleteTicker(symbol: String) {
        viewModelScope.launch {
            stocksRepository.delete(symbol)
            stocksLiveList.value?.minusAssign(symbol)
            stocksLiveList.updateValue()
        }
    }

    fun updateStocks() {
        viewModelScope.launch {
            stocksLiveList.value?.let { stockList ->
                if (!stockList.isEmpty())
                    stocksRepository.updateAll(stockList._list)
            }
        }
    }

    fun getCompanyProfile(symbol: String): CompanyProfile? {
        stocksLiveList.value?.let {
            return it.getProfile(symbol)
        }
        return null
    }

}

