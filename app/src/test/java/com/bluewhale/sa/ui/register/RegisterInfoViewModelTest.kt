package com.bluewhale.sa.ui.register

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.LiveDataTestUtil
import com.bluewhale.sa.R
import com.bluewhale.sa.constant.MobileProvider
import com.bluewhale.sa.data.FakeRegisterRepository
import com.bluewhale.sa.data.source.register.DRequestToken
import com.libs.meuuslibs.network.FakeBaseRepository
import com.orhanobut.logger.Logger.t
import io.reactivex.disposables.Disposable
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException

class RegisterInfoViewModelTest {
//    @Rule
//    @JvmField
//    var schedulersRule = SchedulersRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var navigator: RegisterNavigator

    @Mock
    private lateinit var application: Application

    //@Mock
    private lateinit var registerRepository: FakeRegisterRepository

    private lateinit var registerInfoViewModel: RegisterInfoViewModel

    private var testDisposable: Disposable? = null

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        registerRepository = FakeRegisterRepository()

        // Get a reference to the class under test
        registerInfoViewModel = RegisterInfoViewModel(navigator, registerRepository, false)
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

        Assert.assertFalse(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))
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
        registerInfoViewModel.setName("john")
        Assert.assertFalse(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))

        registerInfoViewModel.setPersonalCode1("900927")
        Assert.assertFalse(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))

        registerInfoViewModel.setPersonalCode2("1")
        Assert.assertFalse(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))

        registerInfoViewModel.setPhone("01067423129")
        Assert.assertFalse(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))

        registerInfoViewModel.setProvider(MobileProvider.SKT)

        printResult("passableTest2")
        Assert.assertTrue(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))
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
        registerInfoViewModel.setName("john")
        registerInfoViewModel.setPersonalCode1("90092")
        registerInfoViewModel.setPersonalCode2("1")
        registerInfoViewModel.setPhone("01067423129")
        registerInfoViewModel.setProvider(MobileProvider.SKT)

        printResult("passableTest3")

        Assert.assertFalse(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))
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
        registerInfoViewModel.setName("john")
        registerInfoViewModel.setPersonalCode1("900927")
        registerInfoViewModel.setPersonalCode2("1")
        registerInfoViewModel.setPhone("0106742312")
        registerInfoViewModel.setProvider(MobileProvider.SKT)

        printResult("passableTest4")

        Assert.assertFalse(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))
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
        registerInfoViewModel.setName("john")
        registerInfoViewModel.setPersonalCode1("900927")
        registerInfoViewModel.setPersonalCode2("1")
        registerInfoViewModel.setPhone("01067423129")
        registerInfoViewModel.setProvider(MobileProvider.SKT)

        printResult("passableTest5")

        Assert.assertTrue(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))
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
        registerInfoViewModel.setName("joe")
        registerInfoViewModel.setPersonalCode1("900927")
        registerInfoViewModel.setPersonalCode2("1")
        registerInfoViewModel.setPhone("01067423129")
        registerInfoViewModel.setProvider(MobileProvider.SKT)

        val testObserver = registerInfoViewModel.requestSMS().test()

        printResult("requestTokenTest1")
        testObserver.assertNotComplete()
//        testObserver.assertError(FakeBaseRepository.getErrorException("NICE.AUTH_FAILED"))
//        testObserver.assertError(t-> t == FakeBaseRepository.getErrorException("NICE.AUTH_FAILED"))
//        Assert.assertEquals(LiveDataTestUtil.getValue(registerInfoViewModel.errorPopup), R.string.ERROR_NICE_AUTH_FAILED)

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
        registerInfoViewModel.setName("john")
        registerInfoViewModel.setPersonalCode1("900927")
        registerInfoViewModel.setPersonalCode2("1")
        registerInfoViewModel.setPhone("01067423129")
        registerInfoViewModel.setProvider(MobileProvider.SKT)

        val testObserver = registerInfoViewModel.requestSMS().test()

        printResult("requestTokenTest2")
        testObserver.assertComplete()
    }

    private fun printResult(title: String) {
        println(title)
        println("name : ${registerInfoViewModel.items.value?.name}")
        println("personalCode1 : ${registerInfoViewModel.items.value?.personalCode1}")
        println("personalCode2 : ${registerInfoViewModel.items.value?.personalCode2}")
        println("phone : ${registerInfoViewModel.items.value?.phone}")
        println("provider : ${registerInfoViewModel.items.value?.provider}")
        println("\n")
    }
}