package com.example.cryptocoins.ui.exchangedetails

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
import com.example.cryptocoins.databinding.ActivityExchangeDetailsBinding
import com.example.cryptocoins.domain.Exchange2
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val EXCHANGE_ID = "EXCHANGE_ID"

@AndroidEntryPoint
class ExchangeDetailsActivity : AppCompatActivity() {

    private var exchangeId: String = ""
    private val exchangeDetailsViewModel: ExchangeDetailsViewModel by viewModels()

    private lateinit var binding: ActivityExchangeDetailsBinding

    companion object {

        fun createIntent(context: Context, exchangeId: String): Intent {
            val intent = Intent(context, ExchangeDetailsActivity::class.java)
            intent.putExtra(EXCHANGE_ID, exchangeId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras.apply {
            exchangeId = this?.getString(EXCHANGE_ID, "") ?: ""
        }

        binding = ActivityExchangeDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24px)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exchangeDetailsViewModel.getExchangeDetails(exchangeId)

        exchangeDetailsViewModel.viewCommand.observe(this, Observer { handleCommand(it) })
        exchangeDetailsViewModel.viewState.observe(this, Observer { handleState(it) })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                exchangeDetailsViewModel.onBackClicked()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleCommand(command: ExchangeDetailsViewModel.ViewCommand) {
        when (command) {
            is ExchangeDetailsViewModel.ViewCommand.CloseScreen -> {
                onBackPressed()
            }
        }
    }

    private fun handleState(state: ExchangeDetailsViewModel.ViewState) {
        when (state) {
            is ExchangeDetailsViewModel.ViewState.Success -> {
                showContent(state.exchange)
            }
            is ExchangeDetailsViewModel.ViewState.Error -> {
                showError()
            }
        }
    }

    private fun showContent(exchange: Exchange2) = with(binding) {
        coverImageView.load(exchange.image) {
            transformations(CircleCropTransformation())
        }

        nameTextView.text = exchange.name
        urlTextView.text = exchange.url
        twitterHandleTextView.text = "@${exchange.twitter_handle}"
    }

    private fun showError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.oops_something_went_wrong), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.reload), {
                exchangeDetailsViewModel.getExchangeDetails(exchangeId)
            })
            .show()
    }

}