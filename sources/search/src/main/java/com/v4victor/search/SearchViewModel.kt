package com.v4victor.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v4victor.core.dto.SearchInfo
import com.v4victor.network.StocksApi
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    enum class MarsApiStatus { LOADING, ERROR, DONE }

    private val _status = MutableLiveData<MarsApiStatus>()


    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()
    private val _properties = MutableLiveData<List<SearchInfo>>()
    val properties: LiveData<List<SearchInfo>>
        get() = _properties
    /**l
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    fun getMarsRealEstateProperties(company: String) =
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            _response.value = "fds"
            try {
                val list = StocksApi.retrofitService.getNames(company)
                _properties.value = list.result
                _response.value = "Success: Mars properties retrieved"
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _response.value = e.message
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }

        }
}