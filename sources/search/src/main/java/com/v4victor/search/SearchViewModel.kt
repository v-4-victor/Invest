package com.v4victor.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v4victor.core.dto.SearchInfo
import com.v4victor.network.StocksApi
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<ApiStatus>()


    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()
    private val _properties = MutableLiveData<List<SearchInfo>>()
    val properties: LiveData<List<SearchInfo>>
        get() = _properties

    fun getTickets(company: String) =
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            _response.value = "fds"
            try {
                val list = StocksApi.retrofitService.getNames(company)
                _properties.value = list.result
                _response.value = "Success: Stocks retrieved"
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _response.value = e.message
                _status.value = ApiStatus.ERROR
                _properties.value = ArrayList()
            }

        }
}