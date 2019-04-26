package com.bluewhale.sa

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.network.api.ShiftAPI
import com.bluewhale.sa.ui.shift.ShiftViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(val api: ShiftAPI): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return Injection.provideShiftRepository(api) as T
    }
}