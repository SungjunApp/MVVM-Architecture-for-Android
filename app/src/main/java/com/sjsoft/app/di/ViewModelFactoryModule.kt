package com.sjsoft.app.di

import com.sjsoft.app.navigator.BaseNavigator
import com.sjsoft.app.navigator.Navigator
import com.sjsoft.app.network.api.APIMyAsset
import com.sjsoft.app.network.api.APIRegister
import com.sjsoft.app.network.api.APITrade
import com.sjsoft.app.network.api.ShiftAPI
import com.sjsoft.app.ui.MainActivity
import com.sjsoft.app.ui.asset.MyAssetNavigator
import com.sjsoft.app.ui.asset.MyAssetViewModel
import com.sjsoft.app.ui.register.RegisterAgreementViewModel
import com.sjsoft.app.ui.register.RegisterInfoViewModel
import com.sjsoft.app.ui.register.RegisterNavigator
import com.sjsoft.app.ui.register.RegisterSMSViewModel
import com.sjsoft.app.ui.shift.ShiftViewModelFactory
import com.sjsoft.app.ui.trade.TradeDetailViewModel
import com.sjsoft.app.ui.trade.TradeHomeViewModel
import com.sjsoft.app.ui.trade.TradeNavigator
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

    @Provides
    fun provideTradeDetailViewModelFactory(
        navigator: TradeNavigator,
        api: APITrade
    ): TradeDetailViewModel.TradeDetailViewModelFactory {
        return TradeDetailViewModel.TradeDetailViewModelFactory(navigator, api)
    }

}