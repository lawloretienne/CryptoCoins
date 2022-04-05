package com.example.cryptocoins

import androidx.lifecycle.Observer
import com.example.cryptocoins.data.network.toResponseModels
import com.example.cryptocoins.data.respositories.coin.CoinRepository
import com.example.cryptocoins.fakers.CoinFaker
import com.example.cryptocoins.ui.coins.CoinsViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
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

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeEach
    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(mainThreadSurrogate)
        coinsViewModel = CoinsViewModel(coinRepository)
    }

    @AfterEach
    override fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `getCoins should Success`() = runBlocking {
        // 1. (Given) Set up conditions required for the test
        coinsViewModel.viewState.observeForever(stateObserver)
        val coins = CoinFaker.list()

        coEvery { coinRepository.getCoins() }
            .answers { coins.toResponseModels() }

        // 2. (When) Then perform one or more actions
        launch(Dispatchers.Main) {
            coinsViewModel.getCoins()
        }

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verifySequence {
            stateObserver.onChanged(CoinsViewModel.ViewState.Loading)
            stateObserver.onChanged(any<CoinsViewModel.ViewState.Success>())
        }
    }

//    @Test
//    fun `getCoins should Error`() = runBlocking {
//        // 1. (Given) Set up conditions required for the test
//        coinsViewModel.viewState.observeForever(stateObserver)
//        val throwable = Exception()
//
//        coEvery { coinRepository.getCoins() }
//            .answers { throwable }
//
//        // 2. (When) Then perform one or more actions
//        coinsViewModel.getCoins()
//
//        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
//        verifySequence {
//            stateObserver.onChanged(CoinsViewModel.ViewState.Loading)
//            stateObserver.onChanged(CoinsViewModel.ViewState.Error)
//        }
//    }

    @Test
    fun `onCoinClicked should ShowCoinDetails`() = runBlocking {
        // 1. (Given) Set up conditions required for the test
        coinsViewModel.viewCommand.observeForever(commandObserver)
        val coin = CoinFaker.basic()

        // 2. (When) Then perform one or more actions
        coinsViewModel.onCoinClicked(coin)

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verify { commandObserver.onChanged(any<CoinsViewModel.ViewCommand.ShowCoinDetails>()) }
    }

}