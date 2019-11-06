package com.sjsoft.app.data.api

import com.sjsoft.app.room.Lotto
import okhttp3.MultipartBody
import retrofit2.http.*

interface RemoteAPI {
    @GET("/common.do?method=getLottoNumber")
    suspend fun getLotto(@Query("drwNo") drwNo: Int): Lotto

    @Multipart
    @POST("v1/images")
    suspend fun uploadImage(@Part partList: List<MultipartBody.Part>)
}