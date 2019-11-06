package com.sjsoft.app.di

import com.sjsoft.app.ui.gallery.GalleryFragment
import com.sjsoft.app.ui.zhome.HomeFragment
import com.sjsoft.app.ui.upload.UploadFragment
import com.sjsoft.app.ui.main.MenuFragment
import com.sjsoft.app.ui.welcome.WelcomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): MenuFragment

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeHistoryFragment(): GalleryFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): UploadFragment
}