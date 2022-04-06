package com.example.cryptocoins.data.respositories.exchange

import com.example.cryptocoins.data.database.dao.ExchangeDao
import com.example.cryptocoins.data.database.entity.ExchangeEntity
import javax.inject.Inject

class ExchangeLocalDataSource @Inject constructor(
    private val exchangeDao: ExchangeDao
) {

    suspend fun getExchanges(): List<ExchangeEntity> {
        return exchangeDao.getAllExchanges()
    }

    suspend fun saveExchanges(exchanges: List<ExchangeEntity>) {
        exchangeDao.insertExchanges(exchangeEntities = exchanges)
    }

    suspend fun getExchange(exchangeId: String): ExchangeEntity {
        return exchangeDao.findExchangeById(exchangeId)
    }

}