package com.example.demo.network

import android.app.Application
import com.bluewhale.sa.data.source.register.DIsUser
import com.bluewhale.sa.data.source.register.DRequestToken
import com.libs.meuuslibs.network.BaseRepository
import io.reactivex.Single

class RegisterRepository(application: Application) : BaseRepository(application), APIRegister {
    override fun requestSMS(
        personalNumber: String,
        name: String,
        mobileProvider: Int,
        mobileNumber: String
    ): Single<DRequestToken> {
        return makeSingleResponse(
            createService(APIRegister::class.java).requestSMS(
                personalNumber,
                name,
                mobileProvider,
                mobileNumber
            ),null)
    }

    override fun verifySMS(authNo: String, token: String): Single<DIsUser> {
        return makeSingleResponse(createService(APIRegister::class.java).verifySMS(authNo, token), null)
    }

    companion object {
        private var INSTANCE: RegisterRepository? = null

        @JvmStatic
        fun getInstance(rootApplication: Application) =
            INSTANCE ?: synchronized(RegisterRepository::class.java) {
                INSTANCE ?: RegisterRepository(rootApplication)
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}