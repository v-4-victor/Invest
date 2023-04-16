package com.v4victor.core

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.updateValue() {
    this.value = this.value
}

fun Double.formatPrice(): String = String.format("%.2f", this)
