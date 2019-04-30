package com.example.demo.network

import com.bluewhale.sa.model.trade.DStockList
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.network.api.APIMyAsset
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