package com.bluewhale.sa.di

import com.bluewhale.sa.MainActivity
import com.bluewhale.sa.ui.shift.work.WorkFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuildModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModule::class])
    fun bindWorkFragment(): WorkFragment

}