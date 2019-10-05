package com.sjsoft.app.di

import com.sjsoft.app.ui.history.HistoryFragment
import com.sjsoft.app.ui.main.MainFragment
import com.sjsoft.app.ui.splash.SplashFragment
import com.sjsoft.app.ui.trend.TrendFragment
import com.sjsoft.app.ui.welcome.WelcomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeHistoryFragment(): HistoryFragment
    @ContributesAndroidInjector
    abstract fun contributeTrendFragment(): TrendFragment
}
