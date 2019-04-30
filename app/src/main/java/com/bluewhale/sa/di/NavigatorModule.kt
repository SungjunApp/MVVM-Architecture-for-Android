package com.bluewhale.sa.di

import com.bluewhale.sa.navigator.Navigator
import com.bluewhale.sa.ui.asset.MyAssetNavigator
import com.bluewhale.sa.ui.register.RegisterNavigator
import com.bluewhale.sa.ui.trade.TradeNavigator
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