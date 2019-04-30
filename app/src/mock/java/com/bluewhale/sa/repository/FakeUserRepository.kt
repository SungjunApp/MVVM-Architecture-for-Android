package com.bluewhale.sa.repository

import com.bluewhale.sa.model.register.DPassword
import com.bluewhale.sa.model.register.DSignUp
import com.bluewhale.sa.model.register.DUser
import com.example.demo.network.APIUser
import com.libs.meuuslibs.network.FakeBaseRepository
import io.reactivex.Completable
import io.reactivex.Single

class FakeUserRepository : FakeBaseRepository(), APIUser {
    override fun postUser(dSignUp: DSignUp): Single<DUser> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserWithId(id: String): Single<DUser> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUser(password: DPassword): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private var INSTANCE: FakeUserRepository? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE ?: synchronized(FakeUserRepository::class.java) {
                INSTANCE ?: FakeUserRepository()
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}