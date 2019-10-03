package com.sjsoft.app.network.api

import com.google.gson.annotations.SerializedName
import com.sjsoft.app.model.User
import com.sjsoft.app.model.StudyLog
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIUser {
//    @POST("login")
//    suspend fun login(@Body loginInfo: LoginInfo): LoginResult
    @FormUrlEncoded
    @POST("login")
    suspend fun login(@Field("email") email: String, @Field("password") password: String): LoginResult
}

open class BaseResponse {
    var code = 0
    var message = ""
    var maxsize = 0
}

open class BaseCollection {
    @SerializedName("_id")
    var objectId = ""
    var createdAt = ""
    var updatedAt = ""
}

class LoginInfo(val email: String, val password: String)

class LoginResult : BaseResponse() {
    @SerializedName("userObject")
    val user: User = User()

    @SerializedName("studyLogObject")
    val studyLog: StudyLog = StudyLog()
}