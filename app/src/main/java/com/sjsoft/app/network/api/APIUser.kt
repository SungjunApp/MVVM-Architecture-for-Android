package com.sjsoft.app.network.api

import com.sjsoft.app.model.register.DPassword
import com.sjsoft.app.model.register.DSignUp
import com.sjsoft.app.model.register.DUser
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface APIUser {
    @POST("/Users")
    fun postUser(@Body dSignUp: DSignUp): Single<DUser>

    @GET("/Users/{id}")
    fun getUserWithId(@Path("id") id: String): Single<DUser>

    //@DELETE("users")
    @HTTP(method = "DELETE", path = "/users", hasBody = true)
    fun deleteUser(@Body password: DPassword): Completable
}
