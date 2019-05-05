package com.sjsoft.app.di

import com.sjsoft.app.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildModule{
    @ActivityScope
    @ContributesAndroidInjector(modules = [TestC::class, FragmentBuildModule::class])
    abstract fun bindMainActivity(): MainActivity

}