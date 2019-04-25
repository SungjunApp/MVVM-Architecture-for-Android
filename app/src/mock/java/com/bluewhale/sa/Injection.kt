package com.bluewhale.sa

import android.app.Activity
import android.app.Application
import com.bluewhale.sa.data.FakeRegisterInfoDataSource
import com.bluewhale.sa.data.FakeRegisterSMSDataSource
import com.bluewhale.sa.data.FakeShiftRemoteDataSource
import com.bluewhale.sa.navigator.BaseNavigator
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.data.source.register.RegisterInfoRepository
import com.bluewhale.sa.data.source.register.RegisterSMSRepository

object Injection {
    fun provideShiftRepository(application: Application): ShiftRepository {
        //val api = (application as AppApplication).requestMaker.createService(ShiftAPI::class.java)
        return ShiftRepository.getInstance(
            FakeShiftRemoteDataSource()
        )
    }

    fun provideRegisterInfoRepository(application: Application): RegisterInfoRepository {
        //val api = (application as AppApplication).requestMaker.createService(ShiftAPI::class.java)
        return RegisterInfoRepository.getInstance(
            FakeRegisterInfoDataSource()
        )
    }

    fun provideRegisterSMSRepository(application: Application): RegisterSMSRepository {
        //val api = (application as AppApplication).requestMaker.createService(ShiftAPI::class.java)
        return RegisterSMSRepository.getInstance(
            FakeRegisterSMSDataSource()
        )
    }

    fun createNavigationProvider(activity: Activity): BaseNavigator {
        return BaseNavigator(activity)
    }

//    fun provideSchedulerProvider(): BaseSchedulerProvider {
//        return SchedulerProvider.getInstance()
//    }
}