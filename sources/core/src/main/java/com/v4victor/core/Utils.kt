package com.v4victor.core

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.updateValue() {
    this.value = this.value
}