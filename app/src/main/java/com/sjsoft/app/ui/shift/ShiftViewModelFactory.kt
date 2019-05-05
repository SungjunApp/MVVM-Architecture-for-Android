package com.sjsoft.app.ui.shift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sjsoft.app.network.api.ShiftAPI

class ShiftViewModelFactory constructor(val api: ShiftAPI): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        ShiftViewModel(api) as T
}