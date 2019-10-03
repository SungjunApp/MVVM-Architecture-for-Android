package com.sjsoft.app.di

import com.sjsoft.app.ui.register.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): LoginFragment

}
