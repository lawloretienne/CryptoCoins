package com.example.cryptocoins

import com.example.cryptocoins.data.database.dao.CoinDao
import com.example.cryptocoins.data.database.toEntityModels
import com.example.cryptocoins.data.respositories.coin.CoinLocalDataSource
import com.example.cryptocoins.fakers.BaseFaker.Companion.Fake.faker
import com.example.cryptocoins.fakers.CoinFaker
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CoinLocalDataSourceTest : BaseTest() {

    @RelaxedMockK
    lateinit var coinDao: CoinDao

    private lateinit var coinLocalDataSource: CoinLocalDataSource

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coinLocalDataSource = CoinLocalDataSource(coinDao)
    }

    @Test
    fun `getCoins should return Coins`() = runBlocking {
        val coins = CoinFaker.list()

        coEvery { coinDao.getAllCoins() }
            .answers { coins.toEntityModels() }

        val result = coinLocalDataSource.getCoins()
        assert(result == coins.toEntityModels())
    }

    @Test
    fun `saveCoins should save Coins`() = runBlocking {
        val coins = CoinFaker.list()

        coEvery { coinDao.insertCoins(coins.toEntityModels()) }
            .answers { Unit }

        coinLocalDataSource.saveCoins(coins.toEntityModels())
    }

    @Test
    fun `getCoin should return Coin`() = runBlocking {
        val coin = CoinFaker.basic()
        val coinId = faker.lorem().word()

        coEvery { coinDao.findCoinById(coinId) }
            .answers { coin.toEntityModel() }

        val result = coinLocalDataSource.getCoin(coinId)
        assert(result == coin.toEntityModel())
    }
}