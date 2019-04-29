package com.example.demo.network

import com.bluewhale.sa.data.source.register.DIsUser
import com.bluewhale.sa.data.source.register.DRequestToken
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.libs.meuuslibs.network.BaseRepository
import io.reactivex.Single

class RegisterRepository(
    navi: BaseSchedulerProvider,
    private val apiRegister: APIRegister) : BaseRepository(navi), APIRegister {
    override fun requestSMS(
        personalNumber: String,
        name: String,
        mobileProvider: Int,
        mobileNumber: String
    ): Single<DRequestToken> {
        return makeSingleResponse(
            apiRegister.requestSMS(
                personalNumber,
                name,
                mobileProvider,
                mobileNumber
            ),null)
    }

    override fun verifySMS(authNo: String, token: String): Single<DIsUser> {
        return makeSingleResponse(apiRegister.verifySMS(authNo, token), null)
    }
}