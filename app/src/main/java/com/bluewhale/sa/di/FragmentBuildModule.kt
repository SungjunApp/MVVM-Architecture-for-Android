package com.bluewhale.sa.di

import com.bluewhale.sa.ui.asset.MyAssetFragment
import com.bluewhale.sa.ui.register.RegisterAgreementFragment
import com.bluewhale.sa.ui.register.RegisterInfoFragment
import com.bluewhale.sa.ui.register.RegisterNavigator
import com.bluewhale.sa.ui.register.RegisterSMSFragment
import com.bluewhale.sa.ui.shift.work.WorkFragment
import com.bluewhale.sa.ui.trade.TradeHomeFragment
import com.bluewhale.sa.ui.trade.TradeNavigator
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuildModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, NavigatorModule::class])
    fun bindWorkFragment(): WorkFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, NavigatorModule::class])
    fun bindRegisterAgreementFragment(): RegisterAgreementFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, NavigatorModule::class])
    fun bindRegisterInfoFragment(): RegisterInfoFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, NavigatorModule::class])
    fun bindRegisterSMSFragment(): RegisterSMSFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, NavigatorModule::class])
    fun bindTradeHomeFragment(): TradeHomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, NavigatorModule::class])
    fun bindMyAssetFragment(): MyAssetFragment
}