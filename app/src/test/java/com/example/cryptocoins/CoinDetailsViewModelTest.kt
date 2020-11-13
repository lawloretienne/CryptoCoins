package com.example.cryptocoins

import androidx.lifecycle.Observer
import com.example.cryptocoins.data.respositories.coin.CoinRepository
import com.example.cryptocoins.fakers.BaseFaker.Companion.Fake.faker
import com.example.cryptocoins.fakers.CoinFaker
import com.example.cryptocoins.ui.coindetails.CoinDetailsViewModel
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Single
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

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coinDetailsViewModel = CoinDetailsViewModel(coinRepository)
    }

    @Test
    fun `getCoinDetails should ShowContent`() {
        // 1. (Given) Set up conditions required for the test
        coinDetailsViewModel.viewState.observeForever(stateObserver)
        val coinId = faker.lorem().word()
        val coin = CoinFaker.basic()

        every { coinRepository.getCoin(coinId) }
            .answers { Single.just(coin.toResponseModel()) }

        // 2. (When) Then perform one or more actions
        coinDetailsViewModel.getCoinDetails(coinId)

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verify { stateObserver.onChanged(any<CoinDetailsViewModel.ViewState.Success>()) }
    }

    @Test
    fun `getCoinDetails should ShowError`() {
        // 1. (Given) Set up conditions required for the test
        coinDetailsViewModel.viewState.observeForever(stateObserver)
        val coinId = faker.lorem().word()
        val throwable = Exception()

        every { coinRepository.getCoin(coinId) }
            .answers { Single.error(throwable) }

        // 2. (When) Then perform one or more actions
        coinDetailsViewModel.getCoinDetails(coinId)

        // 3. (Then) Afterwards, verify that the state you are expecting is actually achieved
        verify { stateObserver.onChanged(CoinDetailsViewModel.ViewState.Error) }
    }

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