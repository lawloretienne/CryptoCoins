package com.example.cryptocoins.ui.coins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.cryptocoins.databinding.FragmentCoinsBinding
import com.example.cryptocoins.domain.Coin
import com.example.cryptocoins.ui.coindetails.CoinDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinsFragment : Fragment(), CoinsAdapter.OnItemClickListener {

    private val coinsViewModel: CoinsViewModel by viewModels()
    private val coinsAdapter = CoinsAdapter()

    private var _binding: FragmentCoinsBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance(extras: Bundle): CoinsFragment {
            val fragment = CoinsFragment()
            fragment.arguments = extras
            return fragment
        }

        @JvmStatic
        fun newInstance(): CoinsFragment {
            return CoinsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contentLayout.recyclerView.apply {
            coinsAdapter.onItemClickListener = this@CoinsFragment
            adapter = coinsAdapter
            addItemDecoration(CoinItemDecoration())
        }

        binding.errorLayout.reloadButton.setOnClickListener {
            coinsViewModel.getCoins()
        }

        coinsViewModel.getCoins()

        coinsViewModel.viewCommand.observe(viewLifecycleOwner, Observer { handleCommand(it) })
        coinsViewModel.viewState.observe(viewLifecycleOwner, Observer { handleState(it) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(coin: Coin) {
        coinsViewModel.onCoinClicked(coin)
    }

    private fun handleCommand(command: CoinsViewModel.ViewCommand) {
        when (command) {
            is CoinsViewModel.ViewCommand.ShowCoinDetails -> {
                val coin = command.coin
                val intent = CoinDetailsActivity.createIntent(requireContext(), coin.id)
                startActivity(intent)
            }
        }
    }

    private fun handleState(state: CoinsViewModel.ViewState) = with( binding) {
        loadingLayout.progressBar.isVisible = state is CoinsViewModel.ViewState.Loading
        errorLayout.errorLinearLayout.isVisible = state is CoinsViewModel.ViewState.Error
        contentLayout.recyclerView.isVisible = state is CoinsViewModel.ViewState.Success

        if(state is CoinsViewModel.ViewState.Success){
            coinsAdapter.submitList(state.coins)
        }
    }
}