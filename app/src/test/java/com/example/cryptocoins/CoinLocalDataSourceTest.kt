package com.example.cryptocoins

import com.example.cryptocoins.data.database.dao.CoinDao
import com.example.cryptocoins.data.database.toEntityModels
import com.example.cryptocoins.data.respositories.coin.CoinLocalDataSource
import com.example.cryptocoins.fakers.BaseFaker.Companion.Fake.faker
import com.example.cryptocoins.fakers.CoinFaker
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
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
    fun `getCoins should return Coins`() {
        val coins = CoinFaker.list()

        every { coinDao.getAllCoins() }
            .answers { coins.toEntityModels() }

        coinLocalDataSource.getCoins()
            .test()
            .assertValue(coins.toEntityModels())
    }

    @Test
    fun `saveCoins should save Coins`() {
        val coins = CoinFaker.list()

        every { coinDao.insertCoins(coins.toEntityModels()) }
            .answers { Unit }

        coinLocalDataSource.saveCoins(coins.toEntityModels())
    }

    @Test
    fun `getCoin should return Coin`() {
        val coin = CoinFaker.basic()
        val coinId = faker.lorem().word()

        every { coinDao.findCoinById(coinId) }
            .answers { coin.toEntityModel() }

        coinLocalDataSource.getCoin(coinId)
            .test()
            .assertValue(coin.toEntityModel())
    }
}