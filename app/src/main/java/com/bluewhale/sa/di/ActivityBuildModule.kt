package com.bluewhale.sa.di

import android.app.Activity
import com.bluewhale.sa.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildModule{
    @ActivityScope
    @ContributesAndroidInjector(modules = [TestC::class, FragmentBuildModule::class])
    abstract fun bindMainActivity(): MainActivity

}