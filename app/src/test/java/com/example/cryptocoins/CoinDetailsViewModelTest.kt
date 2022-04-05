package com.example.cryptocoins

import androidx.lifecycle.Observer
import com.example.cryptocoins.data.respositories.coin.CoinRepository
import com.example.cryptocoins.fakers.BaseFaker.Companion.Fake.faker
import com.example.cryptocoins.fakers.CoinFaker
import com.example.cryptocoins.ui.coindetails.CoinDetailsViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CoinDetailsViewModelTest : BaseTest() {

    @RelaxedMockK
    lateinit var coinRepository: CoinRepository

    @RelaxedMockK
    lateinit var commandObserver: Observer<CoinDetailsViewModel.ViewCommand>

    @RelaxedMockK
    lateinit var stateObserver: Observer<CoinDetailsViewModel.ViewState>

    private lateinit var coinDetailsViewModel: CoinDetailsViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeEach
    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(mainThreadSurrogate)
        coinDetailsViewModel = CoinDetailsViewModel(coinRepository)
    }

    @AfterEach
    override fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `getCoinDetails should ShowContent`() = runBlocking {
        // 1. (Given) Set up conditions required for the test
        coinDetailsViewModel.viewState.observeForever(stateObserver)
        val coinId = faker.lorem().word()
        val coin = CoinFaker.basic()

        coEvery { coinRepository.getCoin(coinId) }
            .answers { coin.toResponseModel() }

        // 2. (When) Then perform one or more actions
        launch(Dispatchers.Main) {
            coinDetailsViewModel.getCoinDetails(coinId)
        }

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verify { stateObserver.onChanged(any<CoinDetailsViewModel.ViewState.Success>()) }
    }

//    @Test
//    fun `getCoinDetails should ShowError`() = runBlocking {
//        // 1. (Given) Set up conditions required for the test
//        coinDetailsViewModel.viewState.observeForever(stateObserver)
//        val coinId = faker.lorem().word()
//        val throwable = Exception()
//
//        coEvery { coinRepository.getCoin(coinId) }
//            .answers { throwable }
//
//        // 2. (When) Then perform one or more actions
//        coinDetailsViewModel.getCoinDetails(coinId)
//
//        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
//        verify { stateObserver.onChanged(CoinDetailsViewModel.ViewState.Error) }
//    }

    @Test
    fun `onBackClicked should CloseScreen`() {
        // 1. (Given) Set up conditions required for the test
        coinDetailsViewModel.viewCommand.observeForever(commandObserver)

        // 2. (When) Then perform one or more actions
        coinDetailsViewModel.onBackClicked()

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verify { commandObserver.onChanged(CoinDetailsViewModel.ViewCommand.CloseScreen) }
    }

}