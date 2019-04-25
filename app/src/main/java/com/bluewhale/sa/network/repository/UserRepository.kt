package com.example.demo.network

import android.app.Application
import com.bluewhale.sa.data.source.register.DPassword
import com.bluewhale.sa.data.source.register.DSignUp
import com.bluewhale.sa.data.source.register.DUser
import com.libs.meuuslibs.network.BaseRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserRepository(val application: Application) : BaseRepository(application), APIUser {
    override fun postUser(dSignUp: DSignUp): Single<DUser> {
        return makeSingleResponse(createService(APIUser::class.java).postUser(dSignUp),
            object : SingleProvider<DUser> {
                override fun onService(it: DUser): DUser {
                    return it
                }
            })
    }

    override fun getUserWithId(id: String): Single<DUser> {
        return makeSingleResponse(createService(APIUser::class.java).getUserWithId(id),
            object : SingleProvider<DUser> {
                override fun onService(it: DUser): DUser {
                    return it
                }
            })
    }

    override fun deleteUser(password: DPassword): Completable {
        return makeCompletableResponse(createService(APIUser::class.java).deleteUser(password))
    }
}