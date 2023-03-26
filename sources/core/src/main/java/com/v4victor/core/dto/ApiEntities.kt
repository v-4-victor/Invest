package com.v4victor.core.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PriceInfo(
    @SerialName("o") val openPrice: Double,
    @SerialName("c") var currentPrice: Double,
)

@Entity
data class CompanyProfile(
    @PrimaryKey
    val symbol: String,
    val companyName: String,
    var openPrice: Double = 0.0,
    var currentPrice: Double = 0.0,
    var favourite: Boolean = false,
    var timeUpdated: Long = 0,
    val description: String = ""
) {
    constructor(searchData: SearchInfo) : this(
        searchData.symbol,
        searchData.companyName,
        description = searchData.description
    )

    operator fun plusAssign(priceInfo: PriceInfo) {
        openPrice = priceInfo.openPrice
        currentPrice = priceInfo.currentPrice
    }
}

data class SearchInfo(
    val symbol: String,
    @SerialName("displaySymbol") val companyName: String,
    val description: String
)

@Serializable
data class Data(
    @SerialName("s") val symbol: String = "",
    @SerialName("p") val currentPrice: Double = 0.0,
    @SerialName("t") val timeUpdated: Long = 0
)