package com.sjsoft.app.di

import com.sjsoft.app.ui.history.HistoryFragment
import com.sjsoft.app.ui.home.HomeFragment
import com.sjsoft.app.ui.search.SearchFragment
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
    abstract fun contributeMainFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeHistoryFragment(): HistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeTrendFragment(): TrendFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}