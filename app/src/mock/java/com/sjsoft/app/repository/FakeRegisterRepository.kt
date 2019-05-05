package com.sjsoft.app.repository

import com.sjsoft.app.constant.MobileProvider
import com.sjsoft.app.model.register.DIsUser
import com.sjsoft.app.model.register.DRequestToken
import com.sjsoft.app.network.api.APIRegister
import com.libs.meuuslibs.network.FakeBaseRepository
import io.reactivex.Single

class FakeRegisterRepository : FakeBaseRepository(), APIRegister {
    override fun requestSMS(
        personalNumber: String,
        name: String,
        mobileProvider: Int,
        mobileNumber: String
    ): Single<DRequestToken> {

        val isCollectPersonalNumber = personalNumber == "9009271"
        val isCollectName = name == "john"
        val isCollectProviderId = mobileProvider == MobileProvider.SKT.providerCode
        val isCollectMobileNumber = mobileNumber == "01067423129"

        return if (isCollectPersonalNumber && isCollectName && isCollectProviderId && isCollectMobileNumber)
            Single.just(DRequestToken("PASS"))
        else
            Single.error(getErrorException("NICE.AUTH_FAILED"))
    }

    override fun verifySMS(authNo: String, token: String): Single<DIsUser> {
        val isCollectCode = (token == testToken) && (authNo == "test00")

        return if (isCollectCode)
            Single.just(DIsUser("test", false))
        else
            Single.error(getErrorException("NICE.INVALID_TOKEN"))
    }

    companion object {
        const val testToken = "PASS"

        private var INSTANCE: FakeRegisterRepository? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE
                ?: synchronized(FakeRegisterRepository::class.java) {
                INSTANCE
                    ?: FakeRegisterRepository()
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}


//
//override fun verifyCode(
//    token: String,
//    marketingClause: Boolean,
//    authCode: String,
//    callback: RegisterSMSDataSource.CompletableCallback
//) {
//}