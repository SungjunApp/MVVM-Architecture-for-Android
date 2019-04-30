package com.example.demo.network

import android.app.Application
import com.bluewhale.sa.data.source.trade.DStockList
import com.libs.meuuslibs.network.BaseRepository
import io.reactivex.Single

class MyAssetRepository(val application: Application) : BaseRepository(application), APIMyAsset {
    override fun getMyAssetList(): Single<DStockList> {
        return makeSingleResponse(createService(APIMyAsset::class.java).getMyAssetList(), null)
    }

    companion object {
        private var INSTANCE: MyAssetRepository? = null

        @JvmStatic
        fun getInstance(rootApplication: Application) =
            INSTANCE ?: synchronized(MyAssetRepository::class.java) {
                INSTANCE ?: MyAssetRepository(rootApplication)
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}