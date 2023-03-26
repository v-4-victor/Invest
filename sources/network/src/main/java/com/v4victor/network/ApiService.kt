package com.v4victor.network



import com.v4victor.core.BASE_URL
import com.v4victor.core.TOKEN
import com.v4victor.core.dto.Chart
import com.v4victor.core.dto.News
import com.v4victor.core.dto.PriceInfo
import com.v4victor.core.dto.SearchInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


data class Response(val result: List<SearchInfo>)

interface ApiService {

    @GET("index/constituents")
    suspend fun getTop(
        @Query("symbol") symbol: String = "^DJI",
        @Query("token") token: String = TOKEN
    ): List<String>

    @GET("quote")
    suspend fun getPrice(
        @Query("symbol") symbol: String,
        @Query("token") token: String = TOKEN
    ): PriceInfo

    @GET("company-news")
    suspend fun getNews(
        @Query("symbol") symbol: String,
        @Query("from") fromDate: String,
        @Query("to") toDate: String,
        @Query("token") token: String = TOKEN
    ): List<News>

    @GET("stock/candle")
    suspend fun getCandles(
        @Query("symbol") symbol: String,
        @Query("resolution") resolution: String = "D",
        @Query("from") fromDate: String = "1572651390",
        @Query("to") toDate: String = "1575243390",
        @Query("token") token: String = TOKEN
    ): Chart

    @GET("search")
    suspend fun getNames(
        @Query("q") symbol: String = "apple",
        @Query("token") token: String = TOKEN
    ): Response
}


object StocksApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}