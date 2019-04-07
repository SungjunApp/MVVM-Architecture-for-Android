package com.sjsoft.workmanagement

import android.app.Application
import com.sjsoft.workmanagement.data.source.ShiftRepository
import com.sjsoft.workmanagement.data.source.remote.ShiftRemoteDataSource
import com.sjsoft.workmanagement.network.api.ShiftAPI

object Injection {
    fun provideShiftRepository(application: Application): ShiftRepository {
        val api = (application as AppApplication).requestMaker.createService(ShiftAPI::class.java)
        return ShiftRepository.getInstance(
            ShiftRemoteDataSource(api)
        )
    }
}