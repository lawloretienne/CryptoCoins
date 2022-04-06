package com.example.cryptocoins.data.respositories.exchange

import com.example.cryptocoins.data.network.CoinsService
import com.example.cryptocoins.data.network.response.Exchange2Response
import com.example.cryptocoins.data.network.response.ExchangeResponse
import javax.inject.Inject

class ExchangeRemoteDataSource@Inject constructor(
    private val coinService: CoinsService
) {
    suspend fun getExchanges() : List<ExchangeResponse> {
        return coinService.getExchanges()
    }

    suspend fun getExchange2(exchangeId: String) : Exchange2Response {
        return coinService.getExchange(exchangeId)
    }
}