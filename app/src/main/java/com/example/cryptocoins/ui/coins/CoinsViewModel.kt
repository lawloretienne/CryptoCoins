package com.example.cryptocoins.ui.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocoins.core.common.SingleLiveEvent
import com.example.cryptocoins.data.respositories.coin.CoinRepository
import com.example.cryptocoins.domain.Coin
import com.example.cryptocoins.domain.toDomainModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinRepository: CoinRepository) : ViewModel() {

    val viewState: LiveData<ViewState>
        get() = _viewState
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewCommand: LiveData<ViewCommand>
        get() = _viewCommand
    private val _viewCommand: SingleLiveEvent<ViewCommand> = SingleLiveEvent()

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        class Success(val coins: List<Coin>): ViewState()
    }

    sealed class ViewCommand {
        class ShowCoinDetails(val coin: Coin): ViewCommand()
    }

    fun getCoins() {
        _viewState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                try {
                    var coins = coinRepository.getCoins()
                    _viewState.value = ViewState.Success(coins.toDomainModels())
                } catch (e: Exception) {
                    Timber.e(e)
                    _viewState.value = ViewState.Error
                }
            }
        }
    }

    fun onCoinClicked(coin: Coin) {
        _viewCommand.value = ViewCommand.ShowCoinDetails(coin)
    }

}