package com.example.cryptocoins.ui.coindetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import coil.transform.CircleCropTransformation
import com.example.cryptocoins.R
import com.example.cryptocoins.databinding.ActivityCoinDetailsBinding
import com.example.cryptocoins.domain.Coin
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val COIN_ID = "COIN_ID"

@AndroidEntryPoint
class CoinDetailsActivity : AppCompatActivity() {

    private var coinId: String = ""
    private val coinDetailsViewModel: CoinDetailsViewModel by viewModels()

    private lateinit var binding: ActivityCoinDetailsBinding

    companion object {

        fun createIntent(context: Context, coinId: String): Intent {
            val intent = Intent(context, CoinDetailsActivity::class.java)
            intent.putExtra(COIN_ID, coinId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras.apply {
            coinId = this?.getString(COIN_ID, "") ?: ""
        }

        binding = ActivityCoinDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24px)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        coinDetailsViewModel.getCoinDetails(coinId)

        coinDetailsViewModel.viewCommand.observe(this, Observer { handleCommand(it) })
        coinDetailsViewModel.viewState.observe(this, Observer { handleState(it) })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                coinDetailsViewModel.onBackClicked()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleCommand(command: CoinDetailsViewModel.ViewCommand) {
        when (command) {
            is CoinDetailsViewModel.ViewCommand.CloseScreen -> {
                onBackPressed()
            }
        }
    }

    private fun handleState(state: CoinDetailsViewModel.ViewState) {
        when (state) {
            is CoinDetailsViewModel.ViewState.Success -> {
                showContent(state.coin)
            }
            is CoinDetailsViewModel.ViewState.Error -> {
                showError()
            }
        }
    }

    private fun showContent(coin: Coin) = with(binding) {
        coverImageView.load(coin.image?.large) {
            transformations(CircleCropTransformation())
        }

        symbolTextView.text = coin.symbol
        nameTextView.text = coin.name
        descriptionTextView.text = coin.description?.en
        genesisDateTextView.text = coin.genesisDate
    }

    private fun showError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.oops_something_went_wrong), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.reload), {
                coinDetailsViewModel.getCoinDetails(coinId)
            })
            .show()
    }

}