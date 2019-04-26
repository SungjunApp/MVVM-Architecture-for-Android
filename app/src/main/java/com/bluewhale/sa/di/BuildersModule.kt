package com.bluewhale.sa.di

import com.bluewhale.sa.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule{
    @ActivityScope
    @ContributesAndroidInjector(modules = [TestC::class])
    abstract fun bindSplashModule(): MainActivity

}