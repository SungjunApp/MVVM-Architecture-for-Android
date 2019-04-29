package com.bluewhale.sa.di

import com.bluewhale.sa.ui.register.RegisterAgreementFragment
import com.bluewhale.sa.ui.register.RegisterInfoFragment
import com.bluewhale.sa.ui.register.RegisterNavigator
import com.bluewhale.sa.ui.register.RegisterSMSFragment
import com.bluewhale.sa.ui.shift.work.WorkFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuildModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    fun bindWorkFragment(): WorkFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    fun bindRegisterAgreementFragment(): RegisterAgreementFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    fun bindRegisterInfoFragment(): RegisterInfoFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    fun bindRegisterSMSFragment(): RegisterSMSFragment
}