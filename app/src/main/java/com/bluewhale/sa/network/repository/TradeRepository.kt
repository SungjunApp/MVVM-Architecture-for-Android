package com.example.demo.network

import android.app.Application
import com.bluewhale.sa.data.source.trade.DStockList
import com.libs.meuuslibs.network.BaseRepository
import io.reactivex.Single

class TradeRepository(val application: Application) : BaseRepository(application), APITrade {
    override fun getStockList(page: Int, pageSize: Int): Single<DStockList> {
        return makeSingleResponse(createService(APITrade::class.java).getStockList(page, pageSize), null)
    }

    override fun getFilteredStockList(name: String): Single<DStockList> {
        return makeSingleResponse(createService(APITrade::class.java).getFilteredStockList(name), null)
    }

    companion object {
        private var INSTANCE: TradeRepository? = null

        @JvmStatic
        fun getInstance(rootApplication: Application) =
            INSTANCE ?: synchronized(TradeRepository::class.java) {
                INSTANCE ?: TradeRepository(rootApplication)
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}