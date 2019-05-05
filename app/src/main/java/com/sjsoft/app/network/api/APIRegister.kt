package com.sjsoft.app.network.api

import com.sjsoft.app.model.register.DRequestToken
import com.sjsoft.app.model.register.DIsUser
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIRegister {
    @FormUrlEncoded
    @POST("/Register/requestSMS")
    fun requestSMS(
        @Field("personalNumber") personalNumber: String,
        @Field("name") name: String,
        @Field("mobileProvider") mobileProvider: Int,
        @Field("mobileNumber") mobileNumber: String
    ): Single<DRequestToken>

    @FormUrlEncoded
    @POST("/Register/verifySMS")
    fun verifySMS(
        @Field("authNo") authNo: String
        , @Field("responseSEQ") token: String
    ): Single<DIsUser>
}
