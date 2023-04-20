package com.v4victor.stocks.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v4victor.core.dto.News
import com.v4victor.network.StocksApi
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    private val _response = MutableLiveData<String>()
    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>>
        get() = _news

    fun getNews(company: String) =
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            _response.value = "no value"
            try {
                val list = StocksApi.retrofitService.getNews()
                    .distinctBy { it.headline }
                _news.value = list
                _response.value = "Success: News retrieved"
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _response.value = e.message
                _status.value = ApiStatus.ERROR
                _news.value = ArrayList()
            }

        }
}