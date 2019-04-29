package com.bluewhale.sa

import android.app.Activity
import android.app.Application
import com.bluewhale.sa.data.FakeShiftRemoteDataSource
import com.bluewhale.sa.data.source.ShiftDataSource
import com.bluewhale.sa.data.source.BaseNavigator
import com.bluewhale.sa.data.source.Navigator
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.network.api.ShiftAPI
import com.bluewhale.sa.data.source.register.RegisterInfoRepository
import com.bluewhale.sa.navigator.BaseNavigator
import com.example.demo.network.RegisterRepository

object Injection {
    fun provideShiftDataSource(api: ShiftAPI): ShiftDataSource {
        //val api = (application as AppApplication).requestMaker.createService(ShiftAPI::class.java)
        return ShiftRepository.getInstance(
            FakeShiftRemoteDataSource()
        )
    }

    fun provideRegisterRepository(application: Application): RegisterRepository {
        return RegisterRepository.getInstance(application)
    }

    fun createNavigationProvider(activity: Activity): BaseNavigator {
        return BaseNavigator(activity)
    }

//    fun provideSchedulerProvider(): BaseSchedulerProvider {
//        return SchedulerProvider.getInstance()
//    }
}