package com.sjsoft.app.network.api

import com.sjsoft.app.model.trade.*
import io.reactivex.Single
import retrofit2.http.*

interface APITrade {
    @GET("/Trade")
    fun getStockList(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<DStockList>

    @GET("/Trade")
    fun getFilteredStockList(@Query("name") name: String): Single<DStockList>

    @GET("/orders")
    fun getTransactionList(@Query("tradeId") tradeId: String): Single<DTransactionList>

    @GET("/orders/:{address}/order-book")
    fun getPriceList(@Path("address") address: String): Single<DPriceList>

    @POST("/orders/match")
    fun orderStock(@Body tradeUnit: DOrder): Single<DPrice>
}
