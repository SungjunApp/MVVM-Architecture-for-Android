package com.bluewhale.sa

import android.app.Activity
import android.app.Application
import com.bluewhale.sa.data.FakeMyAssetRepository
import com.bluewhale.sa.data.FakeRegisterRepository
import com.bluewhale.sa.data.FakeShiftRemoteDataSource
import com.bluewhale.sa.data.FakeTradeRepository
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.navigator.BaseNavigator

object Injection {
    fun provideShiftRepository(application: Application): ShiftRepository {
        return ShiftRepository.getInstance(FakeShiftRemoteDataSource())
    }

    fun provideRegisterRepository(application: Application): FakeRegisterRepository {
        return FakeRegisterRepository.getInstance()
    }

    fun provideTradeRepository(application: Application): FakeTradeRepository {
        return FakeTradeRepository.getInstance()
    }

    fun provideMyAssetRepository(application: Application): FakeMyAssetRepository {
        return FakeMyAssetRepository.getInstance()
    }

    fun createNavigationProvider(activity: Activity): BaseNavigator {
        return BaseNavigator(activity)
    }

//    fun provideSchedulerProvider(): BaseSchedulerProvider {
//        return SchedulerProvider.getInstance()
//    }
}