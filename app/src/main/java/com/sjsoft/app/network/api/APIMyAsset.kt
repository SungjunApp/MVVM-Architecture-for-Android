package com.sjsoft.app.network.api

import com.sjsoft.app.model.trade.DStockList
import io.reactivex.Single
import retrofit2.http.GET

interface APIMyAsset {
    @GET("/MyAsset")
    fun getMyAssetList(): Single<DStockList>
}
