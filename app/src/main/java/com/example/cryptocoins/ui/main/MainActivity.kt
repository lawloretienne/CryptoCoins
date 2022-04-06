package com.example.cryptocoins.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocoins.R
import com.example.cryptocoins.databinding.ActivityMainBinding
import com.example.cryptocoins.ui.coins.CoinsFragment
import com.example.cryptocoins.ui.exchanges.ExchangesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val currentFragment = supportFragmentManager.findFragmentById(R.id.mainContentFrameLayout)
            if (currentFragment == null) {
                loadFragment(item.itemId)
            } else {
                if (!item.isChecked) {
                    item.isChecked = true
                    loadFragment(item.itemId)
                }
            }

            false
        }

        binding.bottomNavigationView.selectedItemId = R.id.action_exchanges
    }

    override fun onBackPressed() = with(binding) {
        if (bottomNavigationView.selectedItemId != R.id.action_exchanges)
            bottomNavigationView.selectedItemId = R.id.action_exchanges
        else
            super.onBackPressed()
    }

    private fun loadFragment(itemId: Int) {
        val fragment = when (itemId) {
            R.id.action_exchanges -> ExchangesFragment.newInstance()
            R.id.action_coins -> CoinsFragment.newInstance()
            else -> ExchangesFragment.newInstance()
        }

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.mainContentFrameLayout, fragment, "")
            .commit()
    }
}