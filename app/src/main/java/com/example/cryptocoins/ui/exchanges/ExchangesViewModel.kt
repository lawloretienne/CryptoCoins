package com.example.cryptocoins.ui.exchanges

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocoins.core.common.SingleLiveEvent
import com.example.cryptocoins.data.respositories.exchange.ExchangeRepository
import com.example.cryptocoins.domain.Exchange
import com.example.cryptocoins.domain.toDomainModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExchangesViewModel @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) : ViewModel() {

    val viewState: LiveData<ViewState>
        get() = _viewState
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewCommand: LiveData<ViewCommand>
        get() = _viewCommand
    private val _viewCommand: SingleLiveEvent<ViewCommand> = SingleLiveEvent()

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        class Success(val exchanges: List<Exchange>): ViewState()
    }

    sealed class ViewCommand {
        class ShowExchangeDetails(val exchange: Exchange): ViewCommand()
    }

    fun getExchanges() {
        _viewState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                try {
                    var exchanges = exchangeRepository.getExchanges()
                    _viewState.value = ViewState.Success(exchanges.toDomainModels())
                } catch (e: Exception) {
                    Timber.e(e)
                    _viewState.value = ViewState.Error
                }
            }
        }
    }

    fun onExchangeClicked(exchange: Exchange) {
        _viewCommand.value = ViewCommand.ShowExchangeDetails(exchange)
    }

}