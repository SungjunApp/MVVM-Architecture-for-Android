package com.bluewhale.sa.data

import com.bluewhale.sa.data.source.register.DRequestToken
import com.bluewhale.sa.data.source.register.RegisterInfoDataSource
import com.bluewhale.sa.ui.register.RegisterInfoData

class FakeRegisterInfoDataSource : RegisterInfoDataSource {
    override fun requestSMS(
        personalNumber1: String,
        personalNumber2: String,
        name: String,
        providerId: Int,
        mobileNumber: String,
        callback: RegisterInfoDataSource.CompletableCallback
    ) {
        val isCollectPersonalNumber1 = personalNumber1 == "900927"
        val isCollectPersonalNumber2 = personalNumber2 == "1"
        val isCollectName = name == "john"
        val isCollectProviderId = providerId == RegisterInfoData.MobileProvider.SKT.providerCode
        val isCollectMobileNumber = mobileNumber == "01067423129"

        if (isCollectPersonalNumber1 && isCollectPersonalNumber2 && isCollectName && isCollectProviderId && isCollectMobileNumber)
            callback.onComplete(DRequestToken("collect"))
        else
            callback.onError("Information_is_wrong")
    }
}