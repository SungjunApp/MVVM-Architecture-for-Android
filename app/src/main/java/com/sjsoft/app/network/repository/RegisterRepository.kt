package com.example.demo.network

import com.sjsoft.app.model.register.DIsUser
import com.sjsoft.app.model.register.DRequestToken
import com.sjsoft.app.navigator.BaseSchedulerProvider
import com.sjsoft.app.network.api.APIRegister
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
            ))
    }

    override fun verifySMS(authNo: String, token: String): Single<DIsUser> {
        return makeSingleResponse(apiRegister.verifySMS(authNo, token))
    }
}