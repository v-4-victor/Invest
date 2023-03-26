package com.v4victor.core.dto

import kotlinx.serialization.SerialName

data class Chart(
    @SerialName("o") val openPrice: List<Double>,
    @SerialName("h") val highPrice: List<Double>,
    @SerialName("l") val lowPrice: List<Double>,
    @SerialName("c") val closePrice: List<Double>,
    @SerialName("t") val time: List<Long>
)