package com.bluewhale.sa

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.network.api.ShiftAPI
import com.bluewhale.sa.ui.shift.ShiftViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModule {
    @Provides
    fun provideShiftViewModelFactory(api: ShiftAPI): ViewModelFactory {
        return ViewModelFactory(api)
    }
}