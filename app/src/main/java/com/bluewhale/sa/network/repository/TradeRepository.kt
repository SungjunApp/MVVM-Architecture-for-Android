package com.example.demo.network

import com.bluewhale.sa.model.trade.*
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.network.api.APITrade
import com.libs.meuuslibs.network.BaseRepository
import io.reactivex.Single

class TradeRepository(
    navi: BaseSchedulerProvider,
    val apiTrade: APITrade
) : BaseRepository(navi), APITrade {
    override fun getStockList(page: Int, pageSize: Int): Single<DStockList> {
        return makeSingleResponse(apiTrade.getStockList(page, pageSize))
    }

    override fun getFilteredStockList(name: String): Single<DStockList> {
        return makeSingleResponse(apiTrade.getFilteredStockList(name))
    }

    override fun getPriceList(address: String): Single<DPriceList> {
        return makeSingleResponse(apiTrade.getPriceList(address))
    }

    override fun getTransactionList(tradeId: String): Single<DTransactionList> {
        return makeSingleResponse(apiTrade.getTransactionList(tradeId))
    }

    override fun orderStock(tradeUnit: DOrder): Single<DPrice> {
        return makeSingleResponse(apiTrade.orderStock(tradeUnit))
    }

}