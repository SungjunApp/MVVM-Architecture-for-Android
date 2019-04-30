package com.example.demo.network

import android.app.Application
import com.bluewhale.sa.data.source.trade.DStockList
import com.bluewhale.sa.navigator.BaseSchedulerProvider
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