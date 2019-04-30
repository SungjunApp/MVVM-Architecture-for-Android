package com.bluewhale.sa.ui.asset

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.Injection
import com.example.demo.network.APIMyAsset
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MyAssetViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mViewModel: MyAssetViewModel

    @Mock
    private lateinit var mNavigator: MyAssetNavigator

    //@Mock
    private lateinit var mRepository: APIMyAsset

    @Mock
    private lateinit var mApplication: Application

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        mRepository = Injection.provideMyAssetRepository(mApplication)

        // Get a reference to the class under test
        mViewModel = MyAssetViewModel(mNavigator, mRepository)
    }

    /**
     * get list pages
     *
     * listSize : 30
     * lastItemId : id(30)
     */
    @Test
    fun getListTest() {
        val testObserver = mViewModel.getList().test()
        testObserver.assertComplete()
        printValues("getListTest")
    }


    private fun printValues(title: String) {
        println(title)
        println("listSize : ${mViewModel.assetList.value?.size}")
        println("lastItemId : ${mViewModel.assetList.value?.last()?._id}")
        println("\n")
    }

}