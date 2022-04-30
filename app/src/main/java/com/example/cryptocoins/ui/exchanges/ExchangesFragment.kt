package com.example.cryptocoins.ui.exchanges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.cryptocoins.databinding.FragmentCoinsBinding
import com.example.cryptocoins.databinding.FragmentExchangesBinding
import com.example.cryptocoins.domain.Coin
import com.example.cryptocoins.domain.Exchange
import com.example.cryptocoins.ui.coindetails.CoinDetailsActivity
import com.example.cryptocoins.ui.coins.CoinsViewModel
import com.example.cryptocoins.ui.exchangedetails.ExchangeDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangesFragment : Fragment(), ExchangesAdapter.OnItemClickListener {

    private val exchangesViewModel: ExchangesViewModel by viewModels()
    private val exchangesAdapter = ExchangesAdapter()

    private var _binding: FragmentExchangesBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance(extras: Bundle): ExchangesFragment {
            val fragment = ExchangesFragment()
            fragment.arguments = extras
            return fragment
        }

        @JvmStatic
        fun newInstance(): ExchangesFragment {
            return ExchangesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExchangesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contentLayout.recyclerView.apply {
            exchangesAdapter.onItemClickListener = this@ExchangesFragment
            adapter = exchangesAdapter
            addItemDecoration(ExchangeItemDecoration())
        }

        binding.errorLayout.reloadButton.setOnClickListener {
            exchangesViewModel.getExchanges()
        }

        exchangesViewModel.getExchanges()

        exchangesViewModel.viewCommand.observe(viewLifecycleOwner, Observer { handleCommand(it) })
        exchangesViewModel.viewState.observe(viewLifecycleOwner, Observer { handleState(it) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(exchange: Exchange) {
        exchangesViewModel.onExchangeClicked(exchange)
    }

    private fun handleCommand(command: ExchangesViewModel.ViewCommand) {
        when (command) {
            is ExchangesViewModel.ViewCommand.ShowExchangeDetails -> {
                val exchange = command.exchange
                val intent = ExchangeDetailsActivity.createIntent(requireContext(), exchange.id)
                startActivity(intent)
            }
        }
    }

    private fun handleState(state: ExchangesViewModel.ViewState) = with( binding) {
        loadingLayout.progressBar.isVisible = state is ExchangesViewModel.ViewState.Loading
        errorLayout.errorLinearLayout.isVisible = state is ExchangesViewModel.ViewState.Error
        contentLayout.recyclerView.isVisible = state is ExchangesViewModel.ViewState.Success

        if(state is ExchangesViewModel.ViewState.Success){
            exchangesAdapter.submitList(state.exchanges)
        }
    }

}