package com.bluewhale.sa.di

import com.bluewhale.sa.navigator.BaseNavigator
import com.bluewhale.sa.navigator.Navigator
import com.bluewhale.sa.network.api.ShiftAPI
import com.bluewhale.sa.ui.MainActivity
import com.bluewhale.sa.ui.asset.MyAssetNavigator
import com.bluewhale.sa.ui.asset.MyAssetViewModel
import com.bluewhale.sa.ui.register.RegisterAgreementViewModel
import com.bluewhale.sa.ui.register.RegisterInfoViewModel
import com.bluewhale.sa.ui.register.RegisterNavigator
import com.bluewhale.sa.ui.register.RegisterSMSViewModel
import com.bluewhale.sa.ui.shift.ShiftViewModelFactory
import com.bluewhale.sa.ui.trade.TradeHomeViewModel
import com.bluewhale.sa.ui.trade.TradeNavigator
import com.example.demo.network.APIMyAsset
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
    ): RegisterAgreementViewModel.RegisterAgreementViewModelFactory {
        return RegisterAgreementViewModel.RegisterAgreementViewModelFactory(navigator)
    }

    @Provides
    fun provideRegisterSMSViewModelFactory(
        navigator: RegisterNavigator,
        api: APIRegister
    ): RegisterSMSViewModel.RegisterSMSViewModelFactory {
        return RegisterSMSViewModel.RegisterSMSViewModelFactory(navigator, api)
    }

    @Provides
    fun provideRegisterInfoViewModelFactory(
        navigator: RegisterNavigator,
        api: APIRegister
    ): RegisterInfoViewModel.RegisterInfoViewModelFactory {
        return RegisterInfoViewModel.RegisterInfoViewModelFactory(navigator, api)
    }

    @Provides
    fun provideTradeHomeViewModelFactory(
        navigator: TradeNavigator,
        api: APITrade
    ): TradeHomeViewModel.TradeHomeViewModelFactory {
        return TradeHomeViewModel.TradeHomeViewModelFactory(navigator, api)
    }

    @Provides
    fun provideMyAssetViewModelFactory(
        navigator: MyAssetNavigator,
        api: APIMyAsset
    ): MyAssetViewModel.MyAssetViewModelFactory {
        return MyAssetViewModel.MyAssetViewModelFactory(navigator, api)
    }

}