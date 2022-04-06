package com.example.cryptocoins.data.respositories.exchange

import com.example.cryptocoins.data.database.toEntityModels
import com.example.cryptocoins.data.network.response.CoinResponse
import com.example.cryptocoins.data.network.response.Exchange2Response
import com.example.cryptocoins.data.network.response.ExchangeResponse
import com.example.cryptocoins.data.network.toResponseModels
import javax.inject.Inject

class ExchangeRepository @Inject constructor(
    private val exchangeRemoteDataSource: ExchangeRemoteDataSource,
    private val exchangeLocalDataSource: ExchangeLocalDataSource
) {

    suspend fun getExchanges(): List<ExchangeResponse> {
        val local = exchangeLocalDataSource.getExchanges().toResponseModels()
        return if (local.isEmpty()) {
            val remote = exchangeRemoteDataSource.getExchanges()
            if(remote.isNotEmpty())
                exchangeLocalDataSource.saveExchanges(remote.toEntityModels())
            remote
        } else {
            local
        }
    }

    suspend fun getExchange(exchangeId: String): Exchange2Response {
//        val local = exchangeLocalDataSource.getExchange2(exchangeId).toResponseModel()
        val remote =
            exchangeRemoteDataSource.getExchange2(exchangeId)
//        return local.onErrorResumeNext { remote }
        return remote
    }
}