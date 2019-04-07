package com.sjsoft.workmanagement.network.api

import com.sjsoft.workmanagement.data.Shift
import com.sjsoft.workmanagement.data.ShiftHalf
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShiftAPI {
    @POST("shift/start")
    fun start(@Body marker: ShiftHalf): Call<String>

    @POST("shift/end")
    fun end(@Body marker: ShiftHalf): Call<String>

    @GET("shifts")
    fun shifts(): Call<List<Shift>>
}