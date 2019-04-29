package com.bluewhale.sa.data

import com.bluewhale.sa.data.source.trade.DStock
import com.bluewhale.sa.data.source.trade.DStockList
import com.example.demo.network.APITrade
import com.libs.meuuslibs.network.FakeBaseRepository
import io.reactivex.Single

class FakeTradeRepository : FakeBaseRepository(), APITrade {
    override fun getStockList(page: Int, pageSize: Int): Single<DStockList> {
        val tempList = arrayListOf<DStock>()
        for (i in 1..pageSize) {
            val tempValue = page * pageSize + i

            val tempStock = DStock(
                "id($tempValue)",
                "stock($tempValue)",
                "${tempValue * 1000}",
                "+${tempValue.toFloat() / 10}%",
                "${tempValue * 1000000}",
                "${tempValue * 100000000}"
            )
            tempList.add(tempStock)
        }

        val dStockList = DStockList(tempList)
        return Single.just(dStockList)
    }

    override fun getFilteredStockList(name: String): Single<DStockList> {
        val tempList = arrayListOf<DStock>()
        for (i in 1..30) {

            val tempStock = DStock(
                "id($i)",
                "${name}Stock($i)",
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
        private var INSTANCE: FakeTradeRepository? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE ?: synchronized(FakeTradeRepository::class.java) {
                INSTANCE ?: FakeTradeRepository()
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}