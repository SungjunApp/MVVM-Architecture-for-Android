package com.bluewhale.sa.ui.register

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.Injection
import com.bluewhale.sa.LiveDataTestUtil
import com.bluewhale.sa.constant.MobileProvider
import com.example.demo.network.APIRegister
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RegisterInfoViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mViewModel: RegisterInfoViewModel

    @Mock
    private lateinit var mNavigator: RegisterNavigator

    //@Mock
    private lateinit var mRepository: APIRegister

    @Mock
    private lateinit var mApplication: Application

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        mRepository = Injection.provideRegisterRepository(mApplication)

        // Get a reference to the class under test
        mViewModel = RegisterInfoViewModel(mNavigator, mRepository, false)
    }

    /**
     * default
     *
     * name : null
     * personalCode1 : null
     * personalCode2 : null
     * phone : null
     * provider : UNSELECTED
     *
     * nextButton -> false
     */
    @Test
    fun passableTest1() {
        printResult("passableTest1")

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
    }

    /**
     * name : null -> "john"
     * personalCode1 : null -> "900927"
     * personalCode2 : null -> "1"
     * phone : null -> "01067423129"
     * provider : UNSELECTED -> SKT
     *
     * nextButton -> false -> true
     */
    @Test
    fun passableTest2() {
        mViewModel.setName("john")
        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))

        mViewModel.setPersonalCode1("900927")
        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))

        mViewModel.setPersonalCode2("1")
        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))

        mViewModel.setPhone("01067423129")
        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))

        mViewModel.setProvider(MobileProvider.SKT)

        printResult("passableTest2")
        Assert.assertTrue(LiveDataTestUtil.getValue(mViewModel.nextButton))
    }

    /**
     * length of personalCode1 is not enough
     *
     * name : "john"
     * personalCode1 : "90092"
     * personalCode2 : "1"
     * phone : "01067423129"
     * provider : SKT
     *
     * nextButton -> true
     */
    @Test
    fun passableTest3() {
        mViewModel.setName("john")
        mViewModel.setPersonalCode1("90092")
        mViewModel.setPersonalCode2("1")
        mViewModel.setPhone("01067423129")
        mViewModel.setProvider(MobileProvider.SKT)

        printResult("passableTest3")

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
    }

    /**
     * length of phone is not enough
     *
     * name : "john"
     * personalCode1 : "900927"
     * personalCode2 : "1"
     * phone : "0106742312"
     * provider : SKT
     *
     * nextButton -> true
     */
    @Test
    fun passableTest4() {
        mViewModel.setName("john")
        mViewModel.setPersonalCode1("900927")
        mViewModel.setPersonalCode2("1")
        mViewModel.setPhone("0106742312")
        mViewModel.setProvider(MobileProvider.SKT)

        printResult("passableTest4")

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
    }

    /**
     * name : "john"
     * personalCode1 : "900927"
     * personalCode2 : "1"
     * phone : "01067423129"
     * provider : SKT
     *
     * nextButton -> true
     */
    @Test
    fun passableTest5() {
        mViewModel.setName("john")
        mViewModel.setPersonalCode1("900927")
        mViewModel.setPersonalCode2("1")
        mViewModel.setPhone("01067423129")
        mViewModel.setProvider(MobileProvider.SKT)

        printResult("passableTest5")

        Assert.assertTrue(LiveDataTestUtil.getValue(mViewModel.nextButton))
    }

    /**
     * Wrong Information
     *
     * name : "joe"
     * personalCode1 : "900927"
     * personalCode2 : "1"
     * phone : "01067423129"
     * provider : SKT
     *
     * response -> Error(error(R.string.ERROR_NICE_AUTH_FAILED))
     */
    @Test
    fun requestTokenTest1() {
        mViewModel.setName("joe")
        mViewModel.setPersonalCode1("900927")
        mViewModel.setPersonalCode2("1")
        mViewModel.setPhone("01067423129")
        mViewModel.setProvider(MobileProvider.SKT)

        val testObserver = mViewModel.requestSMS().test()

        printResult("requestTokenTest1")
        testObserver.assertNotComplete()
//        testObserver.assertError(FakeBaseRepository.getErrorException("NICE.AUTH_FAILED"))
//        testObserver.assertError(t-> t == FakeBaseRepository.getErrorException("NICE.AUTH_FAILED"))
//        Assert.assertEquals(LiveDataTestUtil.getValue(mViewModel.errorPopup), R.string.ERROR_NICE_AUTH_FAILED)

    }

    /**
     * Collect Information
     *
     * name : "john"
     * personalCode1 : "900927"
     * personalCode2 : "1"
     * phone : "01067423129"
     * provider : SKT
     *
     * response -> Success()
     */
    @Test
    fun requestTokenTest2() {
        mViewModel.setName("john")
        mViewModel.setPersonalCode1("900927")
        mViewModel.setPersonalCode2("1")
        mViewModel.setPhone("01067423129")
        mViewModel.setProvider(MobileProvider.SKT)

        val testObserver = mViewModel.requestSMS().test()

        printResult("requestTokenTest2")
        testObserver.assertComplete()
    }

    private fun printResult(title: String) {
        println(title)
        println("name : ${mViewModel.items.value?.name}")
        println("personalCode1 : ${mViewModel.items.value?.personalCode1}")
        println("personalCode2 : ${mViewModel.items.value?.personalCode2}")
        println("phone : ${mViewModel.items.value?.phone}")
        println("provider : ${mViewModel.items.value?.provider}")
        println("\n")
    }
}