package com.example.cryptocoins.data.respositories.coin

import com.example.cryptocoins.data.database.toEntityModels
import com.example.cryptocoins.data.network.response.CoinResponse
import com.example.cryptocoins.data.network.response.ExchangeResponse
import com.example.cryptocoins.data.network.toResponseModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource) {

    suspend fun getCoins(): List<CoinResponse> = withContext(Dispatchers.IO) {
        val local = coinLocalDataSource.getCoins().toResponseModels()
        local.ifEmpty {
            val remote = coinRemoteDataSource.getCoins()
            if (remote.isNotEmpty())
                coinLocalDataSource.saveCoins(remote.toEntityModels())
            remote
        }
    }

    suspend fun getCoin(coinId: String): CoinResponse = withContext(Dispatchers.IO) {
        val local = coinLocalDataSource.getCoin(coinId).toResponseModel()
        val remote =
            coinRemoteDataSource.getCoin(coinId)
//        return local.onErrorResumeNext { remote }
        remote
    }
}