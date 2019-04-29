package com.bluewhale.sa.ui.trade

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.Injection
import com.example.demo.network.APITrade
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TradeHomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mViewModel: TradeHomeViewModel

    @Mock
    private lateinit var mNavigator: TradeNavigator

    //@Mock
    private lateinit var mRepository: APITrade

    @Mock
    private lateinit var mApplication: Application

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        mRepository = Injection.provideTradeRepository(mApplication)

        // Get a reference to the class under test
        mViewModel = TradeHomeViewModel(mNavigator, mRepository)
    }

    /**
     * get two pages
     *
     * page : 1 -> 2
     * listSize : 20 -> 40
     * lastItemId : id(40)
     */
    @Test
    fun listPagingTest() {
        val testObserver1 = mViewModel.getList().test()
        testObserver1.assertComplete()
        printValues("listPagingTest1")

        val testObserver2 = mViewModel.getList().test()
        testObserver2.assertComplete()
        printValues("listPagingTest2")
    }

    /**
     * reset and get one page
     *
     * page : 1 -> 2 -> 3 -> 1
     * listSize : 20 -> 40 -> 60-> 20
     * lastItemId : id(20)
     */
    @Test
    fun listResetTest() {
        val testObserver1 = mViewModel.getList().test()
        testObserver1.assertComplete()
        printValues("listResetTest1")

        val testObserver2 = mViewModel.getList().test()
        testObserver2.assertComplete()
        printValues("listResetTest2")

        val testObserver3 = mViewModel.getList().test()
        testObserver3.assertComplete()
        printValues("listResetTest2")

        mViewModel.resetPage()
        val testObserver4 = mViewModel.getList().test()
        testObserver4.assertComplete()
        printValues("listResetTest3")
    }

    /**
     * reset and get one page
     *
     * filter : "seoul"
     *
     * page : 0
     * listSize : 30
     * lastItemId : id(30)
     */
    @Test
    fun listFilterTest() {
        val testObserver = mViewModel.getFilteredList("seoul").test()
        testObserver.assertComplete()
        printValues("listFilterTest")
    }

    /**
     * reset filtered list and get normal one page
     *
     *
     * page : 0 -> 1
     * listSize : 30 -> 20
     * lastItemId : id(20)
     */
    @Test
    fun resetFilteredListToNormalListTest() {
        val testObserver1 = mViewModel.getFilteredList("seoul").test()
        testObserver1.assertComplete()
        printValues("resetFilteredListToNormalListTest1")

        val testObserver2 = mViewModel.getList().test()
        testObserver2.assertComplete()
        printValues("resetFilteredListToNormalListTest2")
    }

    private fun printValues(title: String) {
        println(title)
        println("page : ${mViewModel.tradePage.value}")
        println("listSize : ${mViewModel.tradeList.value?.size}")
        println("isFiltered : ${mViewModel.isFiltered.value}")
        println("lastItemId : ${mViewModel.tradeList.value?.last()?._id}")
        println("\n")
    }

}