package com.example.demo.network

import com.bluewhale.sa.model.trade.*
import com.bluewhale.sa.repository.trade.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APITrade {
    @GET("/Trade")
    fun getStockList(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<DStockList>

    @GET("/Trade")
    fun getFilteredStockList(@Query("name") name: String): Single<DStockList>

    @GET("/Trade/Price")
    fun getPriceList(@Query("tradeId") tradeId: String): Single<DPriceList>

    @GET("/Trade/Transactions")
    fun getTransactionList(@Query("tradeId") tradeId: String): Single<DTransactionList>

    @POST("/Trade/Sell")
    fun sellStock(@Body tradeUnit: DTradeSelect): Single<DPrice>

    @POST("/Trade/Sell/MarketPrice")
    fun sellStockMarketPrice(@Body tradeUnit: DTradeMarketPrice): Single<DPrice>

    @POST("/Trade/Purchase")
    fun purchaseStock(@Body tradeUnit: DTradeSelect): Single<DPrice>

    @POST("/Trade/Purchase/MarketPrice")
    fun purchaseStockMarketPrice(@Body tradeUnit: DTradeMarketPrice): Single<DPrice>
}
