package com.example.demo.network

import com.bluewhale.sa.data.source.register.DPassword
import com.bluewhale.sa.data.source.register.DUser
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface APIUser {
    @POST("/Users")
    fun postUser(@Body dUser: DUser): Single<DUser>

//    @GET("/Users")
//    fun getUsers(): Single<DUser>

    @GET("/Users/{id}")
    fun getUserWithId(@Path("id") id: String): Single<DUser>


    //@DELETE("users")
    @HTTP(method = "DELETE", path = "/users", hasBody = true)
    fun deleteUser(@Body password: DPassword): Completable
}
