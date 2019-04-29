package com.bluewhale.sa.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo.network.APIRegister


class RegisterInfoViewModelFactory constructor(
    val navigator: RegisterNavigator,
    val apiRegister: APIRegister
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        RegisterInfoViewModel(navigator, apiRegister) as T
}