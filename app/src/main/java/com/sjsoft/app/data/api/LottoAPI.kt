package com.sjsoft.app.data.api

import com.sjsoft.app.room.Lotto
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoAPI {
    @GET("/common.do?method=getLottoNumber")
    suspend fun getLotto(@Query("drwNo") drwNo: Int): Lotto
}