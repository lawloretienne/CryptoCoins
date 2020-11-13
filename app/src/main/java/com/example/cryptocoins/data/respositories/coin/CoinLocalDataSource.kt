package com.example.cryptocoins.data.respositories.coin

import com.example.cryptocoins.data.database.dao.CoinDao
import com.example.cryptocoins.data.database.entity.CoinEntity
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CoinLocalDataSource @Inject constructor(
    private val coinDao: CoinDao
) {

    fun getCoins(): Single<List<CoinEntity>> {
        return Single.fromCallable { coinDao.getAllCoins() }
    }

    fun saveCoins(coins: List<CoinEntity>) {
        Completable.fromCallable { coinDao.insertCoins(coinEntities = coins) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun getCoin(coinId: String): Single<CoinEntity> {
        return Single.fromCallable { coinDao.findCoinById(coinId) }
    }
}