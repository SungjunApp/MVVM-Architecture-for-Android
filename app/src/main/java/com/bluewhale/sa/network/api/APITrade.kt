package com.example.demo.network

import com.bluewhale.sa.data.trade.DStockList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APITrade {
    @GET("/Trade")
    fun getStockList(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<DStockList>

    @GET("/Trade")
    fun getFilteredStockList(@Query("name") name: String): Single<DStockList>
}
