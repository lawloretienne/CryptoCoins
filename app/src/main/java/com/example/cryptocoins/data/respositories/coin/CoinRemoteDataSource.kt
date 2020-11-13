package com.example.cryptocoins.data.respositories.coin

import com.example.cryptocoins.data.network.CoinsService
import com.example.cryptocoins.data.network.response.CoinResponse
import io.reactivex.Single
import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(
    private val coinService: CoinsService
) {

    fun getCoins() : Single<List<CoinResponse>> {
        return coinService.getCoins()
    }

    fun getCoin(coinId: String) : Single<CoinResponse> {
        return coinService.getCoin(coinId)
    }
}