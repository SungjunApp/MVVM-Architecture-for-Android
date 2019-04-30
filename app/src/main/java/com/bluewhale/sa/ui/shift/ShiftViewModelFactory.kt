package com.bluewhale.sa.ui.shift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.Injection
import com.bluewhale.sa.network.api.ShiftAPI
import com.bluewhale.sa.ui.register.RegisterAgreementViewModel

class ShiftViewModelFactory constructor(val api: ShiftAPI): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        ShiftViewModel(api) as T
}