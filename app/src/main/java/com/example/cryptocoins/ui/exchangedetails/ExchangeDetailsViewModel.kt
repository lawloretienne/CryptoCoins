package com.example.cryptocoins.ui.exchangedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocoins.core.common.SingleLiveEvent
import com.example.cryptocoins.data.respositories.exchange.ExchangeRepository
import com.example.cryptocoins.domain.Exchange2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExchangeDetailsViewModel @Inject constructor(
    val exchangeRepository: ExchangeRepository) : ViewModel() {

    val viewState: LiveData<ViewState>
        get() = _viewState
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewCommand: LiveData<ViewCommand>
        get() = _viewCommand
    private val _viewCommand: SingleLiveEvent<ViewCommand> = SingleLiveEvent()

    sealed class ViewState {
        object Error : ViewState()
        class Success(val exchange: Exchange2): ViewState()
    }

    sealed class ViewCommand {
        object CloseScreen : ViewCommand()
    }

    fun getExchangeDetails(exchangeId: String) {
        viewModelScope.launch {
            runCatching {
                try {
                    var exchange = exchangeRepository.getExchange(exchangeId)
                    _viewState.value = ViewState.Success(exchange.toDomainModel())
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