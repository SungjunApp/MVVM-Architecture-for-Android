package com.bluewhale.sa.data

import com.bluewhale.sa.R
import com.bluewhale.sa.data.source.register.DUser
import com.bluewhale.sa.data.source.register.RegisterSMSDataSource

class FakeRegisterSMSDataSource : RegisterSMSDataSource {
    companion object {
        const val testToken = "PASS"

        val testUser = DUser("test", false)
    }

    override fun verifyCode(
        token: String,
        marketingClause: Boolean,
        authCode: String,
        callback: RegisterSMSDataSource.CompletableCallback
    ) {
        val isCollectCode = (token == testToken) && (authCode == "test00")

        if (isCollectCode) {
            testUser.marketingClause = marketingClause
            callback.onComplete(testUser)
        } else
            callback.onError(R.string.register_invalid_information)
    }
}