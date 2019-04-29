package com.bluewhale.sa.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class RegisterAgreementViewModelFactory constructor(
    val navigator: RegisterNavigator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        RegisterAgreementViewModel(navigator) as T
}