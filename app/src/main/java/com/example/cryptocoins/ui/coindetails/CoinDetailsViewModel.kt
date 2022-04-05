package com.example.cryptocoins.ui.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocoins.core.common.SingleLiveEvent
import com.example.cryptocoins.data.respositories.coin.CoinRepository
import com.example.cryptocoins.domain.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    val coinRepository: CoinRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

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
        compositeDisposable.add(
            coinRepository.getCoin(coinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ coin ->
                    _viewState.value = ViewState.Success(coin.toDomainModel())
                }, {
                    Timber.e(it)
                    _viewState.value = ViewState.Error
                })

        )
    }

    fun onBackClicked() {
        _viewCommand.value = ViewCommand.CloseScreen
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}