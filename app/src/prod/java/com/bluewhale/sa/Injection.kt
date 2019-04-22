package com.bluewhale.sa

import android.app.Application
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.data.source.remote.ShiftRemoteDataSource
import com.bluewhale.sa.network.api.ShiftAPI

object Injection {
    fun provideShiftRepository(application: Application): ShiftRepository {
        val api = (application as AppApplication).requestMaker.createService(ShiftAPI::class.java)
        return ShiftRepository.getInstance(
            ShiftRemoteDataSource(api)
        )
    }
}