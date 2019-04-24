package com.bluewhale.sa.data

import com.bluewhale.sa.R
import com.bluewhale.sa.data.source.register.DUser
import com.bluewhale.sa.data.source.register.RegisterSMSDataSource

class FakeRegisterSMSDataSource : RegisterSMSDataSource {
    companion object {
        const val testToken = "PASS"
    }

    override fun verifyCode(
        token: String,
        marketingClause: Boolean,
        authCode: String,
        callback: RegisterSMSDataSource.CompletableCallback
    ) {
        val isCollectCode = (token == testToken) && (authCode == "test")

        if (isCollectCode)
            callback.onComplete(DUser("test", marketingClause))
        else
            callback.onError(R.string.register_invalid_information)
    }
}