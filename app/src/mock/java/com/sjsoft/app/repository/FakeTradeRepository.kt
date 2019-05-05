package com.sjsoft.app.repository

import com.sjsoft.app.constant.Side
import com.sjsoft.app.model.trade.*
import com.sjsoft.app.network.api.APITrade
import com.libs.meuuslibs.network.FakeBaseRepository
import io.reactivex.Single
import java.math.BigDecimal

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

    override fun getPriceList(address: String): Single<DPriceList> {
        val tempList = arrayListOf<DPrice>()
        for (i in 0..29) {

//            val tempStock = DPrice(
//                "id(address$i)",
//                "testStock($i)",
//                BigDecimal(390 - 10 * i),
//                BigDecimal(10 + i),
//                if (i < 15) DPrice.PriceStatus.SELL else DPrice.PriceStatus.BUY
//            )
//            tempList.add(tempStock)
        }

        val dPriceList = DPriceList(tempList)
        return Single.just(dPriceList)
    }

    override fun getTransactionList(tradeId: String): Single<DTransactionList> {
        val tempList = arrayListOf<DTransaction>()
        for (i in 0..9) {

            val tempStock = DTransaction(
                "id(tradeId$i)",
                BigDecimal(100 + 10 * i),
                BigDecimal(19 - i),
                1000000000L + i,
                1000000000L + i

            )
            tempList.add(tempStock)
        }

        val dPriceList = DTransactionList(tempList)
        return Single.just(dPriceList)
    }

    private val testPrice1 = DPrice(
        BigDecimal(200),
        BigDecimal(100),
        Side.BUY
    )

    private val testPrice2 = DPrice(
        BigDecimal(190),
        BigDecimal(100),
        Side.SELL
    )

    override fun orderStock(tradeUnit: DOrder): Single<DPrice> {
//        if (testPrice1.side == Side.SELL)
//            testPrice1.amount.add(tradeUnit.amount)
//        else
//            testPrice1.amount.subtract(tradeUnit.amount)
        return Single.just(testPrice1)
    }

    companion object {
        private var INSTANCE: FakeTradeRepository? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE
                ?: synchronized(FakeTradeRepository::class.java) {
                INSTANCE
                    ?: FakeTradeRepository()
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}