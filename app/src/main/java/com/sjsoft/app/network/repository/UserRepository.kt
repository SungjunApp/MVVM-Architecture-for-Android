package com.example.demo.network

import com.sjsoft.app.model.register.DPassword
import com.sjsoft.app.model.register.DSignUp
import com.sjsoft.app.model.register.DUser
import com.sjsoft.app.navigator.BaseSchedulerProvider
import com.sjsoft.app.network.api.APIUser
import com.libs.meuuslibs.network.BaseRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserRepository(
    navi: BaseSchedulerProvider,
    private val apiUser: APIUser
) : BaseRepository(navi), APIUser {
    override fun postUser(dSignUp: DSignUp): Single<DUser> {
        return makeSingleResponse(apiUser.postUser(dSignUp))
    }

    override fun getUserWithId(id: String): Single<DUser> {
        return makeSingleResponse(apiUser.getUserWithId(id))
    }

    override fun deleteUser(password: DPassword): Completable {
        return makeCompletableResponse(apiUser.deleteUser(password))
    }
}