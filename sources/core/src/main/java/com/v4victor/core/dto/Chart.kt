package com.v4victor.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Chart(
    @SerialName("o") val openPrice: List<Double>,
    @SerialName("h") val highPrice: List<Double>,
    @SerialName("l") val lowPrice: List<Double>,
    @SerialName("c") val closePrice: List<Double>,
    @SerialName("t") val time: List<Long>
)
//@Serializable
//data class Chart(
//    val o: List<Double>,
//    val h: List<Double>,
//    val l: List<Double>,
//    val c: List<Double>,
//    val t: List<Long>
//)