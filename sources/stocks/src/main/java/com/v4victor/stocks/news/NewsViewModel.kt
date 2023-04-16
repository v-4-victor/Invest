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
    private val _properties = MutableLiveData<List<News>>()
    val properties: LiveData<List<News>>
        get() = _properties

    fun getMarsRealEstateProperties(company:String) =
            viewModelScope.launch {
                _status.value = ApiStatus.LOADING
                _response.value = "fds"
                try {
                    val list = StocksApi.retrofitService.getNews(company,"2022-01-01","2023-01-31").distinctBy { it.headline }
                    _properties.value = list
                    _response.value = "Success: Mars properties retrieved"
                    _status.value = ApiStatus.DONE
                } catch (e: Exception) {
                    _response.value =e.message
                    _status.value = ApiStatus.ERROR
                    _properties.value = ArrayList()
                }

            }
}