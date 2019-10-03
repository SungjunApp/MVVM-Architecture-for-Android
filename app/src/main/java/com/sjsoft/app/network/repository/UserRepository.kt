package com.sjsoft.app.network.repository

import com.sjsoft.app.model.User
import com.sjsoft.app.network.api.APIUser
import com.sjsoft.app.network.api.LoginInfo
import com.sjsoft.app.network.api.LoginResult
import javax.inject.Inject
import javax.inject.Singleton

interface UserDataSource{
    suspend fun login(email:String, password:String): LoginResult
}

class UserRepository constructor(
    private val apiUser: APIUser
) : UserDataSource {
    override suspend fun login(email:String, password:String): LoginResult {
        return apiUser.login(email, password)
    }
}