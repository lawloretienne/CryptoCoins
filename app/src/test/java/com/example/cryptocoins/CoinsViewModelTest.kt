package com.example.cryptocoins

import androidx.lifecycle.Observer
import com.example.cryptocoins.data.network.toResponseModels
import com.example.cryptocoins.data.respositories.coin.CoinRepository
import com.example.cryptocoins.fakers.CoinFaker
import com.example.cryptocoins.ui.coins.CoinsViewModel
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.mockk.verifySequence
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CoinsViewModelTest : BaseTest() {

    @RelaxedMockK
    lateinit var coinRepository: CoinRepository

    @RelaxedMockK
    lateinit var commandObserver: Observer<CoinsViewModel.ViewCommand>

    @RelaxedMockK
    lateinit var stateObserver: Observer<CoinsViewModel.ViewState>

    private lateinit var coinsViewModel: CoinsViewModel

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coinsViewModel = CoinsViewModel(coinRepository)
    }

    @Test
    fun `getRestaurants should ShowRestaurantDetails`() {
        // 1. (Given) Set up conditions required for the test
        coinsViewModel.viewState.observeForever(stateObserver)
        val coins = CoinFaker.list()

        every { coinRepository.getCoins() }
            .answers { Single.just(coins.toResponseModels()) }

        // 2. (When) Then perform one or more actions
        coinsViewModel.getCoins()

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verifySequence {
            stateObserver.onChanged(CoinsViewModel.ViewState.Loading)
            stateObserver.onChanged(any<CoinsViewModel.ViewState.Success>())
        }
    }

    @Test
    fun `getCoins should ShowError`() {
        // 1. (Given) Set up conditions required for the test
        coinsViewModel.viewState.observeForever(stateObserver)
        val throwable = Exception()

        every { coinRepository.getCoins() }
            .answers { Single.error(throwable) }

        // 2. (When) Then perform one or more actions
        coinsViewModel.getCoins()

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verifySequence {
            stateObserver.onChanged(CoinsViewModel.ViewState.Loading)
            stateObserver.onChanged(CoinsViewModel.ViewState.Error)
        }
    }

    @Test
    fun `onCoinClicked should ShowCoinDetails`() {
        // 1. (Given) Set up conditions required for the test
        coinsViewModel.viewCommand.observeForever(commandObserver)
        val restaurant = CoinFaker.basic()

        // 2. (When) Then perform one or more actions
        coinsViewModel.onCoinClicked(restaurant)

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verify { commandObserver.onChanged(any<CoinsViewModel.ViewCommand.ShowCoinDetails>()) }
    }

}