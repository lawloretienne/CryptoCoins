package com.example.cryptocoins.data.respositories.coin

import com.example.cryptocoins.data.database.toEntityModels
import com.example.cryptocoins.data.network.response.CoinResponse
import com.example.cryptocoins.data.network.toResponseModels
import io.reactivex.Single
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource) {

    fun getCoins(): Single<List<CoinResponse>> {
        val local = coinLocalDataSource.getCoins().map { it.toResponseModels() }
            .map {
                if(it.isEmpty())
                    throw NoSuchElementException("No coins found")
                it
            }
        val remote =
            coinRemoteDataSource.getCoins()
                .doOnSuccess {
                    coinLocalDataSource.saveCoins(it.toEntityModels())
                }
        return local.onErrorResumeNext { remote }
//        return remote
    }

    fun getCoin(coinId: String): Single<CoinResponse> {
        val local = coinLocalDataSource.getCoin(coinId).map { it.toResponseModel() }
        val remote =
            coinRemoteDataSource.getCoin(coinId)
//        return local.onErrorResumeNext { remote }
        return remote
    }
}