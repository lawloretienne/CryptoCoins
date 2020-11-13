package com.example.cryptocoins.ui.coins

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.cryptocoins.R
import com.example.cryptocoins.domain.Coin
import com.example.cryptocoins.ui.coindetails.CoinDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.coins_content_layout.*
import kotlinx.android.synthetic.main.coins_loading_layout.*
import kotlinx.android.synthetic.main.error_layout.*

@AndroidEntryPoint
class CoinsActivity : AppCompatActivity(), CoinsAdapter.OnItemClickListener {

    private val coinsViewModel: CoinsViewModel by viewModels()
    private val coinsAdapter = CoinsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_coins)

        recyclerView.apply {
            coinsAdapter.onItemClickListener = this@CoinsActivity
            adapter = coinsAdapter
            addItemDecoration(CoinItemDecoration())
        }

        reloadButton.setOnClickListener {
            coinsViewModel.getCoins()
        }

        coinsViewModel.getCoins()

        coinsViewModel.viewCommand.observe(this, Observer { handleCommand(it) })
        coinsViewModel.viewState.observe(this, Observer { handleState(it) })

    }

    override fun onItemClick(coin: Coin) {
        coinsViewModel.onCoinClicked(coin)
    }

    private fun handleCommand(command: CoinsViewModel.ViewCommand) {
        when (command) {
            is CoinsViewModel.ViewCommand.ShowCoinDetails -> {
                val coin = command.coin
                val intent = CoinDetailsActivity.createIntent(this, coin.id)
                startActivity(intent)
            }
        }
    }

    private fun handleState(state: CoinsViewModel.ViewState) {
        when (state) {
            is CoinsViewModel.ViewState.Loading -> {
                showLoading()
                hideError()
                hideContent()
            }
            is CoinsViewModel.ViewState.Error -> {
                showError()
                hideLoading()
                hideContent()
            }
            is CoinsViewModel.ViewState.Success -> {
                coinsAdapter.submitList(state.coins)
                showContent()
                hideError()
                hideLoading()
            }
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun showError() {
        errorLinearLayout.visibility = View.VISIBLE
    }

    private fun hideError() {
        errorLinearLayout.visibility = View.GONE
    }

    private fun showContent() {
        recyclerView.visibility = View.VISIBLE
    }

    private fun hideContent() {
        recyclerView.visibility = View.GONE
    }
}