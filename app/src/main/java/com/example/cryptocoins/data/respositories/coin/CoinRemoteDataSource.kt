package com.example.cryptocoins.data.respositories.coin

import com.example.cryptocoins.data.network.CoinsService
import com.example.cryptocoins.data.network.response.CoinResponse
import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(
    private val coinService: CoinsService
) {

    suspend fun getCoins() : List<CoinResponse> {
        return coinService.getCoins()
    }

    suspend fun getCoin(coinId: String) : CoinResponse {
        return coinService.getCoin(coinId)
    }
}