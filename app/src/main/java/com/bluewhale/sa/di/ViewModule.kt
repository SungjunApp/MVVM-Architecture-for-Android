package com.bluewhale.sa.di

import com.bluewhale.sa.ViewModelFactory
import com.bluewhale.sa.network.api.ShiftAPI

//@Module
class ViewModule {
    //@Provides
    fun provideShiftViewModelFactory(api: ShiftAPI): ViewModelFactory {
        return ViewModelFactory(api)
    }
}