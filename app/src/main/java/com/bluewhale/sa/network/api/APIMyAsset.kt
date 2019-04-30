package com.example.demo.network

import com.bluewhale.sa.data.trade.DStockList
import io.reactivex.Single
import retrofit2.http.GET

interface APIMyAsset {
    @GET("/MyAsset")
    fun getMyAssetList(): Single<DStockList>
}
