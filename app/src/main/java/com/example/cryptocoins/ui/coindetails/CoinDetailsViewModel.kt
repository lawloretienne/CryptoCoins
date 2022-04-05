package com.example.cryptocoins.ui.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocoins.core.common.SingleLiveEvent
import com.example.cryptocoins.data.respositories.coin.CoinRepository
import com.example.cryptocoins.domain.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    val coinRepository: CoinRepository) : ViewModel() {

    val viewState: LiveData<ViewState>
        get() = _viewState
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewCommand: LiveData<ViewCommand>
        get() = _viewCommand
    private val _viewCommand: SingleLiveEvent<ViewCommand> = SingleLiveEvent()

    sealed class ViewState {
        object Error : ViewState()
        class Success(val coin: Coin): ViewState()
    }

    sealed class ViewCommand {
        object CloseScreen : ViewCommand()
    }

    fun getCoinDetails(coinId: String) {
        viewModelScope.launch {
            runCatching {
                try {
                    var coin = coinRepository.getCoin(coinId)
                    _viewState.value = ViewState.Success(coin.toDomainModel())
                } catch (e: Exception) {
                    Timber.e(e)
                    _viewState.value = ViewState.Error
                }
            }
        }
    }

    fun onBackClicked() {
        _viewCommand.value = ViewCommand.CloseScreen
    }
}