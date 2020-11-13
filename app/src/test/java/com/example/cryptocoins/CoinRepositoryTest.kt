package com.example.cryptocoins

import com.example.cryptocoins.data.database.toEntityModels
import com.example.cryptocoins.data.network.toResponseModels
import com.example.cryptocoins.data.respositories.coin.CoinLocalDataSource
import com.example.cryptocoins.data.respositories.coin.CoinRemoteDataSource
import com.example.cryptocoins.data.respositories.coin.CoinRepository
import com.example.cryptocoins.fakers.BaseFaker.Companion.Fake.faker
import com.example.cryptocoins.fakers.CoinFaker
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
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
    fun `getCoins should return Coins`() {
        val coins = CoinFaker.list()

        every { coinRemoteDataSource.getCoins() }
            .answers { Single.just(coins.toResponseModels())}
        every { coinLocalDataSource.getCoins() }
            .answers { Single.just(coins.toEntityModels())}

        coinRepository.getCoins()
            .test()
            .assertValue(coins.toResponseModels())
    }

    @Test
    fun `getCoin should return Coin`() {
        val coinId = faker.lorem().word()
        val coin = CoinFaker.basic()

        every { coinRemoteDataSource.getCoin(coinId) }
            .answers { Single.just(coin.toResponseModel()) }
        every { coinLocalDataSource.getCoin(coinId) }
            .answers { Single.just(coin.toEntityModel()) }

        coinRepository.getCoin(coinId)
            .test()
            .assertValue(coin.toResponseModel())
    }
}