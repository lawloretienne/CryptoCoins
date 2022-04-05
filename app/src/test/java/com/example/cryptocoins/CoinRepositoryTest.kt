package com.example.cryptocoins

import com.example.cryptocoins.data.database.toEntityModels
import com.example.cryptocoins.data.network.toResponseModels
import com.example.cryptocoins.data.respositories.coin.CoinLocalDataSource
import com.example.cryptocoins.data.respositories.coin.CoinRemoteDataSource
import com.example.cryptocoins.data.respositories.coin.CoinRepository
import com.example.cryptocoins.fakers.BaseFaker.Companion.Fake.faker
import com.example.cryptocoins.fakers.CoinFaker
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CoinRepositoryTest : BaseTest() {

    @MockK
    lateinit var coinRemoteDataSource: CoinRemoteDataSource

    @MockK
    lateinit var coinLocalDataSource: CoinLocalDataSource

    private lateinit var coinRepository: CoinRepository

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coinRepository = CoinRepository(coinRemoteDataSource, coinLocalDataSource)
    }

    @Test
    fun `getCoins should return Coins`() = runBlocking {
        val coins = CoinFaker.list()

        coEvery { coinRemoteDataSource.getCoins() }
            .answers { coins.toResponseModels()}
        coEvery { coinLocalDataSource.getCoins() }
            .answers { coins.toEntityModels()}

        val result = coinRepository.getCoins()
        assert(result == coins.toResponseModels())
    }

    @Test
    fun `getCoin should return Coin`() = runBlocking {
        val coinId = faker.lorem().word()
        val coin = CoinFaker.basic()

        coEvery { coinRemoteDataSource.getCoin(coinId) }
            .answers { coin.toResponseModel() }
        coEvery { coinLocalDataSource.getCoin(coinId) }
            .answers { coin.toEntityModel() }

        val result = coinRepository.getCoin(coinId)
        assert(result == coin.toResponseModel())
    }
}