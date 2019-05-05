package com.sjsoft.app.di

import com.sjsoft.app.model.DWallet
import dagger.Module
import dagger.Provides

@Module
class TestC{
    //@Singleton
    @Provides
    @ActivityScope
    fun getWallet(): DWallet {
        return DWallet()
    }
}