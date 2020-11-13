package com.example.cryptocoins.ui.coins

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocoins.core.common.SingleLiveEvent
import com.example.cryptocoins.data.respositories.coin.CoinRepository
import com.example.cryptocoins.domain.Coin
import com.example.cryptocoins.domain.toDomainModels
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CoinsViewModel @ViewModelInject constructor(
    val coinRepository: CoinRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

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

        compositeDisposable.add(
            coinRepository.getCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ coins ->
                    _viewState.value = ViewState.Success(coins.toDomainModels())
                }, {
                    Timber.e(it)
                    _viewState.value = ViewState.Error
                })

        )
    }

    fun onCoinClicked(coin: Coin) {
        _viewCommand.value = ViewCommand.ShowCoinDetails(coin)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}