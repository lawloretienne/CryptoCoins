package com.example.cryptocoins.data.respositories.coin

import com.example.cryptocoins.data.database.dao.CoinDao
import com.example.cryptocoins.data.database.entity.CoinEntity
import javax.inject.Inject

class CoinLocalDataSource @Inject constructor(
    private val coinDao: CoinDao
) {

    suspend fun getCoins(): List<CoinEntity> {
        return coinDao.getAllCoins()
    }

    suspend fun saveCoins(coins: List<CoinEntity>) {
        coinDao.insertCoins(coinEntities = coins)
    }

    suspend fun getCoin(coinId: String): CoinEntity {
        return coinDao.findCoinById(coinId)
    }
}