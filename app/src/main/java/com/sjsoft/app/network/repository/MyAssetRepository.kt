package com.example.demo.network

import com.sjsoft.app.model.trade.DStockList
import com.sjsoft.app.navigator.BaseSchedulerProvider
import com.sjsoft.app.network.api.APIMyAsset
import com.libs.meuuslibs.network.BaseRepository
import io.reactivex.Single

class MyAssetRepository(
    navi: BaseSchedulerProvider,
    private val api: APIMyAsset
) : BaseRepository(navi), APIMyAsset {
    override fun getMyAssetList(): Single<DStockList> {
        return makeSingleResponse(api.getMyAssetList())
    }
}