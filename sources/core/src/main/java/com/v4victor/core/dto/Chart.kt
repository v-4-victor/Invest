package com.v4victor.core.dto

import com.google.gson.annotations.SerializedName

//@Serializable
data class Chart(
    @SerializedName("o") val openPrice: List<Double>,
    @SerializedName("h") val highPrice: List<Double>,
    @SerializedName("l") val lowPrice: List<Double>,
    @SerializedName("c") val closePrice: List<Double>,
    @SerializedName("t") val time: List<Long>
)
//@Serializable
//data class Chart(
//    val o: List<Double>,
//    val h: List<Double>,
//    val l: List<Double>,
//    val c: List<Double>,
//    val t: List<Long>
//)