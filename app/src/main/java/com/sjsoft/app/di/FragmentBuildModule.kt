package com.sjsoft.app.di

import com.sjsoft.app.ui.asset.MyAssetFragment
import com.sjsoft.app.ui.register.RegisterAgreementFragment
import com.sjsoft.app.ui.register.RegisterInfoFragment
import com.sjsoft.app.ui.register.RegisterSMSFragment
import com.sjsoft.app.ui.shift.work.WorkFragment
import com.sjsoft.app.ui.trade.TradeDetailFragment
import com.sjsoft.app.ui.trade.TradeHomeFragment
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
    fun bindTradeDetailFragment(): TradeDetailFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, NavigatorModule::class])
    fun bindMyAssetFragment(): MyAssetFragment

}