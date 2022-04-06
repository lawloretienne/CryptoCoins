package com.example.cryptocoins.data.respositories.coin

import com.example.cryptocoins.data.database.toEntityModels
import com.example.cryptocoins.data.network.response.CoinResponse
import com.example.cryptocoins.data.network.response.ExchangeResponse
import com.example.cryptocoins.data.network.toResponseModels
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource) {

    suspend fun getCoins(): List<CoinResponse> {
        val local = coinLocalDataSource.getCoins().toResponseModels()
        return if (local.isEmpty()) {
            val remote = coinRemoteDataSource.getCoins()
            if(remote.isNotEmpty())
                coinLocalDataSource.saveCoins(remote.toEntityModels())
            remote
        } else {
            local
        }
    }

    suspend fun getCoin(coinId: String): CoinResponse {
        val local = coinLocalDataSource.getCoin(coinId).toResponseModel()
        val remote =
            coinRemoteDataSource.getCoin(coinId)
//        return local.onErrorResumeNext { remote }
        return remote
    }
}