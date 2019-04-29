package com.bluewhale.sa.di

import com.bluewhale.sa.navigator.BaseNavigator
import com.bluewhale.sa.navigator.Navigator
import com.bluewhale.sa.ui.shift.ShiftViewModelFactory
import com.bluewhale.sa.network.api.ShiftAPI
import com.bluewhale.sa.ui.MainActivity
import com.bluewhale.sa.ui.register.RegisterAgreementViewModelFactory
import com.bluewhale.sa.ui.register.RegisterInfoViewModelFactory
import com.bluewhale.sa.ui.register.RegisterNavigator
import com.bluewhale.sa.ui.register.RegisterSMSViewModelFactory
import com.bluewhale.sa.ui.trade.TradeHomeViewModelFactory
import com.bluewhale.sa.ui.trade.TradeNavigator
import com.example.demo.network.APIRegister
import com.example.demo.network.APITrade
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {
    @Provides
    fun createNavigationProvider(activity: MainActivity): Navigator {
        return BaseNavigator(activity)
    }

    @Provides
    fun provideShiftViewModelFactory(api: ShiftAPI): ShiftViewModelFactory {
        return ShiftViewModelFactory(api)
    }

    @Provides
    fun provideRegisterAgreementViewModelFactory(
        navigator: RegisterNavigator
    ): RegisterAgreementViewModelFactory {
        return RegisterAgreementViewModelFactory(navigator)
    }

    @Provides
    fun provideRegisterSMSViewModelFactory(
        navigator: RegisterNavigator,
        api: APIRegister
    ): RegisterSMSViewModelFactory {
        return RegisterSMSViewModelFactory(navigator, api)
    }

    @Provides
    fun provideRegisterInfoViewModelFactory(
        navigator: RegisterNavigator,
        api: APIRegister
    ): RegisterInfoViewModelFactory {
        return RegisterInfoViewModelFactory(navigator, api)
    }

    @Provides
    fun provideTradeHomeViewModelFactory(
        navigator: TradeNavigator,
        api: APITrade
    ): TradeHomeViewModelFactory {
        return TradeHomeViewModelFactory(navigator, api)
    }


}