package com.sjsoft.app.di

import android.app.Application
import android.content.Context
import com.sjsoft.app.AppApplication
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun bindContext(application: Application): Context


}