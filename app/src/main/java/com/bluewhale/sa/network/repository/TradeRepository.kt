package com.example.demo.network

import com.bluewhale.sa.model.trade.*
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.repository.trade.*
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

    override fun getPriceList(tradeId: String): Single<DPriceList> {
        return makeSingleResponse(apiTrade.getPriceList(tradeId))
    }

    override fun getTransactionList(tradeId: String): Single<DTransactionList> {
        return makeSingleResponse(apiTrade.getTransactionList(tradeId))
    }

    override fun sellStock(tradeUnit: DTradeSelect): Single<DPrice> {
        return makeSingleResponse(apiTrade.sellStock(tradeUnit))
    }

    override fun sellStockMarketPrice(tradeUnit: DTradeMarketPrice): Single<DPrice> {
        return makeSingleResponse(apiTrade.sellStockMarketPrice(tradeUnit))
    }

    override fun purchaseStock(tradeUnit: DTradeSelect): Single<DPrice> {
        return makeSingleResponse(apiTrade.purchaseStock(tradeUnit))
    }

    override fun purchaseStockMarketPrice(tradeUnit: DTradeMarketPrice): Single<DPrice> {
        return makeSingleResponse(apiTrade.purchaseStockMarketPrice(tradeUnit))
    }

}