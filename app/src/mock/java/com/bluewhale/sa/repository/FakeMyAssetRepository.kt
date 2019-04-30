package com.bluewhale.sa.repository

import com.bluewhale.sa.model.trade.DStock
import com.bluewhale.sa.model.trade.DStockList
import com.example.demo.network.APIMyAsset
import com.libs.meuuslibs.network.FakeBaseRepository
import io.reactivex.Single

class FakeMyAssetRepository : FakeBaseRepository(), APIMyAsset {
    override fun getMyAssetList(): Single<DStockList> {
        val tempList = arrayListOf<DStock>()
        for (i in 1..30) {
            val tempStock = DStock(
                "id($i)",
                "stock($i)",
                "${i * 1000}",
                "+${i.toFloat() / 10}%",
                "${i * 1000000}",
                "${i * 100000000}"
            )
            tempList.add(tempStock)
        }

        val dStockList = DStockList(tempList)
        return Single.just(dStockList)
    }

    companion object {
        private var INSTANCE: FakeMyAssetRepository? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE ?: synchronized(FakeMyAssetRepository::class.java) {
                INSTANCE ?: FakeMyAssetRepository()
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}