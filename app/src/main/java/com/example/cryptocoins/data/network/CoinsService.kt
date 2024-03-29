package com.example.cryptocoins.data.network

import com.example.cryptocoins.data.network.response.CoinResponse
import com.example.cryptocoins.data.network.response.Exchange2Response
import com.example.cryptocoins.data.network.response.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinsService {

    @GET("coins/list")
    suspend fun getCoins(): List<CoinResponse>

    @GET("coins/{id}")
    suspend fun getCoin(
        @Path("id") coinId: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = false,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = false
        ): CoinResponse

    @GET("exchanges")
    suspend fun getExchanges(): List<ExchangeResponse>

    @GET("exchanges/{id}")
    suspend fun getExchange(
        @Path("id") coinId: String
    ): Exchange2Response
}