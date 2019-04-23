package com.bluewhale.sa.data

import com.bluewhale.sa.data.source.register.RegisterInfoDataSource
import java.util.*

class FakeRegisterInfoDataSource : RegisterInfoDataSource {
    override fun requestSMS(
        personalNumber1: String,
        personalNumber2: String,
        name: String,
        providerId: Int,
        mobileNumber: String,
        callback: RegisterInfoDataSource.CompletableCallback
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}