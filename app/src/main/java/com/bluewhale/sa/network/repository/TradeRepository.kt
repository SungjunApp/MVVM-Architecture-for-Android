package com.example.demo.network

import android.app.Application
import com.bluewhale.sa.data.source.trade.DStockList
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.ui.register.RegisterNavigator
import com.libs.meuuslibs.network.BaseRepository
import io.reactivex.Single

class TradeRepository(
    navi: BaseSchedulerProvider,
    val apiTrade:APITrade) : BaseRepository(navi), APITrade {
    override fun getStockList(page: Int, pageSize: Int): Single<DStockList> {
        return makeSingleResponse(apiTrade.getStockList(page, pageSize), null)
    }

    override fun getFilteredStockList(name: String): Single<DStockList> {
        return makeSingleResponse(apiTrade.getFilteredStockList(name), null)
    }
}