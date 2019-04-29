package com.bluewhale.sa.di

import com.bluewhale.sa.model.DWallet
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestC{
    //@Singleton
    @Provides
    @ActivityScope
    fun getWallet(): DWallet {
        return DWallet()
    }
}