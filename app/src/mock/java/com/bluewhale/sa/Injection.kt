package com.bluewhale.sa

import android.app.Activity
import android.app.Application
import com.bluewhale.sa.data.FakeRegisterInfoDataSource
import com.bluewhale.sa.data.FakeShiftRemoteDataSource
import com.bluewhale.sa.data.source.BaseNavigator
import com.bluewhale.sa.data.source.Navigator
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.data.source.register.RegisterInfoRepository

object Injection {
    fun provideShiftRepository(application: Application): ShiftRepository {
        //val api = (application as AppApplication).requestMaker.createService(ShiftAPI::class.java)
        return ShiftRepository.getInstance(
            FakeShiftRemoteDataSource()
        )
    }

    fun provideRegisterRepository(application: Application): RegisterInfoRepository {
        //val api = (application as AppApplication).requestMaker.createService(ShiftAPI::class.java)
        return RegisterInfoRepository.getInstance(
            FakeRegisterInfoDataSource()
        )
    }

    fun createNavigationProvider(activity: Activity): BaseNavigator {
        return BaseNavigator(activity)
    }

//    fun provideSchedulerProvider(): BaseSchedulerProvider {
//        return SchedulerProvider.getInstance()
//    }
}