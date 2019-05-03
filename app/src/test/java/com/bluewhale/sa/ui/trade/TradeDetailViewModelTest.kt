package com.bluewhale.sa.ui.trade

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.constant.Side
import com.bluewhale.sa.network.api.APITrade
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.math.BigDecimal

class TradeDetailViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mViewModel: TradeDetailViewModel

    @Mock
    private lateinit var mNavigator: TradeNavigator

    //@Mock
    private lateinit var mRepository: APITrade

    @Before
    fun setupViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        //mRepository = FakeTradeRepository()

        // Get a reference to the class under test
        mViewModel = TradeDetailViewModel(mNavigator, mRepository)
        mViewModel.tradeId = "test"
    }

    /**
     * get price list
     *
     * volume : 30
     *
     * first price : 390
     * first amount : 10
     * first num : SELL
     *
     * :
     *
     * last price : 100
     * last amount : 39
     * last num : PURCHASE
     */
    @Test
    fun getPriceListTest() {
        val testObserver = mViewModel.getPriceList().test()
        testObserver.assertComplete()
        println("volume ${mViewModel.priceList.value?.size}")
        Assert.assertEquals(mViewModel.priceList.value?.size, 30)

        println("first price ${mViewModel.priceList.value?.first()?.price}")
        println("first amount ${mViewModel.priceList.value?.first()?.amount}")
        println("first num ${mViewModel.priceList.value?.first()?.side?.num}")
        Assert.assertEquals(mViewModel.priceList.value?.first()?.price, BigDecimal(390))
        Assert.assertEquals(mViewModel.priceList.value?.first()?.amount, BigDecimal(10))
        Assert.assertEquals(mViewModel.priceList.value?.first()?.side, Side.SELL)

        println("last price ${mViewModel.priceList.value?.last()?.price}")
        println("last amount ${mViewModel.priceList.value?.last()?.amount}")
        println("last num ${mViewModel.priceList.value?.last()?.side?.num}")
        Assert.assertEquals(mViewModel.priceList.value?.last()?.price, BigDecimal(100))
        Assert.assertEquals(mViewModel.priceList.value?.last()?.amount, BigDecimal(39))
        Assert.assertEquals(mViewModel.priceList.value?.last()?.side, Side.BUY)
        println("\n")
    }

    /**
     * get transaction list
     *
     * volume : 10
     *
     * first price : 100
     * first amount : 19
     *
     * :
     *
     * last price : 190
     * last amount : 10
     */
    @Test
    fun getTransactionListTest() {
        val testObserver = mViewModel.getTransactionList().test()
        testObserver.assertComplete()
        println("volume ${mViewModel.transactionList.value?.size}")
        Assert.assertEquals(mViewModel.transactionList.value?.size, 10)

        println("first price ${mViewModel.transactionList.value?.first()?.price}")
        println("first amount ${mViewModel.transactionList.value?.first()?.amount}")
        Assert.assertEquals(mViewModel.transactionList.value?.first()?.price, BigDecimal(100))
        Assert.assertEquals(mViewModel.transactionList.value?.first()?.amount, BigDecimal(19))

        println("last price ${mViewModel.transactionList.value?.last()?.price}")
        println("last amount ${mViewModel.transactionList.value?.last()?.amount}")
        Assert.assertEquals(mViewModel.transactionList.value?.last()?.price, BigDecimal(190))
        Assert.assertEquals(mViewModel.transactionList.value?.last()?.amount, BigDecimal(10))
        println("\n")
    }

    /**
     * set trade price
     *
     * price : 200 -> 100 -> auto(-1)
     * amount : 10 -> 20 -> 20
     */
    @Test
    fun setTradePriceTest() {
        mViewModel.setTradePrice("200")
        mViewModel.setTradeAmount("10")
        println("price ${mViewModel.tradePrice.value}")
        println("price ${mViewModel.tradeAmount.value}")
        Assert.assertEquals(mViewModel.tradePrice.value, BigDecimal(200))
        Assert.assertEquals(mViewModel.tradeAmount.value, BigDecimal(10))

        mViewModel.setTradePrice("100")
        mViewModel.setTradeAmount("20")
        println("price ${mViewModel.tradePrice.value}")
        println("price ${mViewModel.tradeAmount.value}")
        Assert.assertEquals(mViewModel.tradePrice.value, BigDecimal(100))
        Assert.assertEquals(mViewModel.tradeAmount.value, BigDecimal(20))

        mViewModel.setTradeAuto()
        println("price ${mViewModel.tradePrice.value}")
        println("price ${mViewModel.tradeAmount.value}")
        Assert.assertEquals(mViewModel.tradePrice.value, BigDecimal(-1))
        Assert.assertEquals(mViewModel.tradeAmount.value, BigDecimal(20))
        println("\n")
    }

    /**
     * purchase stock
     *
     * price : 200
     * amount : 10
     */
    @Test
    fun purchaseStockTest() {
        mViewModel.setTradePrice("200")
        mViewModel.setTradeAmount("10")
        val testObserver1 = mViewModel.orderStock().test()
        testObserver1.assertComplete()
    }
}