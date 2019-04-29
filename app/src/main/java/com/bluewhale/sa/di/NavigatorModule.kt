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
import com.bluewhale.sa.ui.register.RegisterNavigator
import com.example.demo.network.APIRegister
import com.example.demo.network.APIUser
import com.example.demo.network.RegisterRepository
import com.example.demo.network.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//@Module
class NavigatorModule {
//    @Provides
//    fun createNavigationProvider(activity: MainActivity): Navigator {
//        return BaseNavigator(activity)
//    }
//
//    @Provides
//    fun provideRegisterNavigator(navi: Navigator): RegisterNavigator {
//        return RegisterNavigator(navi)
//    }
}