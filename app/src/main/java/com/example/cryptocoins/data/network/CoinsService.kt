package com.example.cryptocoins.data.network

import com.example.cryptocoins.data.network.response.CoinResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinsService {

    @GET("coins/list")
    fun getCoins(): Single<List<CoinResponse>>

    @GET("coins/{id}")
    fun getCoin(
        @Path("id") coinId: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = false,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = false
        ): Single<CoinResponse>

}