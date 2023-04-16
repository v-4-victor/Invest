package com.v4victor.core.dto

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class PriceInfo(
    @SerializedName("o") val openPrice: Double,
    @SerializedName("c") val currentPrice: Double
)

@Entity
data class CompanyProfile(
    @PrimaryKey
    val symbol: String,
    val description: String,
    var openPrice: Double = 0.0,
    var currentPrice: Double = 0.0,
    var favourite: Boolean = false,
    var timeUpdated: Long = 0,
    val displaySymbol: String = ""
) {
    constructor(searchData: SearchInfo) : this(
        searchData.symbol,
        searchData.description,
        displaySymbol = searchData.displaySymbol
    )

    operator fun plusAssign(priceInfo: PriceInfo) {
        Log.d("Price", priceInfo.toString())
        openPrice = priceInfo.openPrice
        currentPrice = priceInfo.currentPrice
    }
}

data class SearchInfo(
    val symbol: String,
    val description: String,
    val displaySymbol: String
)

@Serializable
data class Data(
    @SerialName("s") val symbol: String = "",
    @SerialName("p") val currentPrice: Double = 0.0,
    @SerialName("t") val timeUpdated: Long = 0
)