package com.example.cryptocoins

import com.example.cryptocoins.data.network.CoinsService
import com.example.cryptocoins.data.network.toResponseModels
import com.example.cryptocoins.data.respositories.coin.CoinRemoteDataSource
import com.example.cryptocoins.fakers.BaseFaker.Companion.Fake.faker
import com.example.cryptocoins.fakers.CoinFaker
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
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
    fun `getCoins should return Coins`() = runBlocking {
        val coins = CoinFaker.list()

        coEvery { coinsService.getCoins() }
            .answers { coins.toResponseModels() }

        val result = coinRemoteDataSource.getCoins()
        assert(result == coins.toResponseModels())
    }

    @Test
    fun `getCoin should return Coin`() = runBlocking {
        val coin = CoinFaker.basic()
        val coinId = faker.lorem().word()

        coEvery { coinsService.getCoin(coinId) }
            .answers { coin.toResponseModel() }

        val result = coinRemoteDataSource.getCoin(coinId)
        assert(result == coin.toResponseModel())
    }
}