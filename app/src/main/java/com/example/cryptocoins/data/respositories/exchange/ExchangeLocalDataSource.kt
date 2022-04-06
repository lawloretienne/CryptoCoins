package com.example.cryptocoins.data.respositories.exchange

import com.example.cryptocoins.data.database.dao.Exchange2Dao
import com.example.cryptocoins.data.database.dao.ExchangeDao
import com.example.cryptocoins.data.database.entity.Exchange2Entity
import com.example.cryptocoins.data.database.entity.ExchangeEntity
import javax.inject.Inject

class ExchangeLocalDataSource @Inject constructor(
    private val exchangeDao: ExchangeDao,
    private val exchange2Dao: Exchange2Dao
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

    suspend fun getExchange2(name: String): Exchange2Entity {
        return exchange2Dao.findExchangeByName(name)
    }

}