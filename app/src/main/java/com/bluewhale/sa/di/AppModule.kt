package com.bluewhale.sa.di

import android.content.Context
import com.bluewhale.sa.AppApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: AppApplication): Context = app
}