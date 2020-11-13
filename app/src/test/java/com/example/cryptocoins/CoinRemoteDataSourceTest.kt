package com.example.cryptocoins

import com.example.cryptocoins.data.network.CoinsService
import com.example.cryptocoins.data.network.toResponseModels
import com.example.cryptocoins.data.respositories.coin.CoinRemoteDataSource
import com.example.cryptocoins.fakers.BaseFaker.Companion.Fake.faker
import com.example.cryptocoins.fakers.CoinFaker
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CoinRemoteDataSourceTest : BaseTest() {

    @RelaxedMockK
    lateinit var coinsService: CoinsService

    private lateinit var coinRemoteDataSource: CoinRemoteDataSource

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coinRemoteDataSource = CoinRemoteDataSource(coinsService)
    }

    @Test
    fun `getCoins should return Coins`() {
        val coins = CoinFaker.list()

        every { coinsService.getCoins() }
            .answers { Single.just(coins.toResponseModels()) }

        coinRemoteDataSource.getCoins()
            .test()
            .assertValue(coins.toResponseModels())
    }

    @Test
    fun `getCoin should return Coin`() {
        val coin = CoinFaker.basic()
        val coinId = faker.lorem().word()

        every { coinsService.getCoin(coinId) }
            .answers { Single.just(coin.toResponseModel()) }

        coinRemoteDataSource.getCoin(coinId)
            .test()
            .assertValue(coin.toResponseModel())
    }
}