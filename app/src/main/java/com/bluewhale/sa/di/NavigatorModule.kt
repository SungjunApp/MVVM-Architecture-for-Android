package com.bluewhale.sa.di

import android.app.Activity
import com.bluewhale.sa.data.source.ShiftDataSource
import com.bluewhale.sa.data.source.remote.ShiftRemoteDataSource
import com.bluewhale.sa.navigator.BaseNavigator
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.navigator.Navigator
import com.bluewhale.sa.navigator.SchedulerProvider
import com.bluewhale.sa.network.api.ShiftAPI
import com.bluewhale.sa.ui.MainActivity
import com.bluewhale.sa.ui.asset.MyAssetNavigator
import com.bluewhale.sa.ui.register.RegisterNavigator
import com.bluewhale.sa.ui.trade.TradeNavigator
import com.example.demo.network.APIRegister
import com.example.demo.network.APIUser
import com.example.demo.network.RegisterRepository
import com.example.demo.network.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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