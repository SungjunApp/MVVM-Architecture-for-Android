package com.example.demo.network

import android.app.Application
import com.bluewhale.sa.data.source.register.DIsUser
import com.bluewhale.sa.data.source.register.DRequestToken
import com.libs.meuuslibs.network.BaseRepository
import io.reactivex.Single

class RegisterRepository(val application: Application) : BaseRepository(application), APIRegister {
    override fun requestSMS(
        personalNumber: String,
        name: String,
        mobileProvider: Int,
        mobileNumber: String
    ): Single<DRequestToken> {
        return makeSingleResponse(createService(APIRegister::class.java).requestSMS(
            personalNumber,
            name,
            mobileProvider,
            mobileNumber
        ),
            object : SingleProvider<DRequestToken> {
                override fun onService(it: DRequestToken): DRequestToken {
                    return it
                }
            })
    }

    override fun verifySMS(authNo: String, token: String): Single<DIsUser> {
        return makeSingleResponse(createService(APIRegister::class.java).verifySMS(authNo, token),
            object : SingleProvider<DIsUser> {
                override fun onService(it: DIsUser): DIsUser {
                    return it
                }
            })
    }
}