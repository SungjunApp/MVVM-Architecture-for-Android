package com.sjsoft.app.di

import com.sjsoft.app.navigator.Navigator
import com.sjsoft.app.ui.asset.MyAssetNavigator
import com.sjsoft.app.ui.register.RegisterNavigator
import com.sjsoft.app.ui.trade.TradeNavigator
import dagger.Module
import dagger.Provides

@Module
class NavigatorModule {
    @Provides
    fun provideRegisterNavigator(navi: Navigator): RegisterNavigator {
        return RegisterNavigator(navi)
    }

    @Provides
    fun provideTradeNavigator(navi: Navigator): TradeNavigator {
        return TradeNavigator(navi)
    }

    @Provides
    fun provideMyAssetNavigator(navi: Navigator): MyAssetNavigator {
        return MyAssetNavigator(navi)
    }

}