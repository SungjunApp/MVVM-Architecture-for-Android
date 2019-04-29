package com.bluewhale.sa.ui.trade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.ui.register.RegisterNavigator
import com.example.demo.network.APITrade


class TradeHomeViewModelFactory constructor(
    val navigator: TradeNavigator,
    val api: APITrade
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        TradeHomeViewModel(navigator, api) as T
}