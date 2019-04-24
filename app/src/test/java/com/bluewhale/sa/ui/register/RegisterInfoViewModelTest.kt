package com.bluewhale.sa.ui.register

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.Injection.provideRegisterRepository
import com.bluewhale.sa.LiveDataTestUtil
import com.bluewhale.sa.constant.MobileProvider
import com.bluewhale.sa.data.source.register.DRequestToken
import com.bluewhale.sa.data.source.register.RegisterInfoDataSource
import com.bluewhale.sa.data.source.register.RegisterInfoRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RegisterInfoViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var navigator: RegisterNavigator

    @Mock
    private lateinit var application: Application

    //@Mock
    private lateinit var registerInfoRepository: RegisterInfoRepository

    private lateinit var registerInfoViewModel: RegisterInfoViewModel

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        registerInfoRepository = provideRegisterRepository(application)

        // Get a reference to the class under test
        registerInfoViewModel = RegisterInfoViewModel(navigator, registerInfoRepository, false)
    }

    /**
     * default
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
     */
    @Test
    fun repositoryTest1() {
        registerInfoViewModel.setName("joe")
        registerInfoViewModel.setPersonalCode1("900927")
        registerInfoViewModel.setPersonalCode2("1")
        registerInfoViewModel.setPhone("01067423129")
        registerInfoViewModel.setProvider(MobileProvider.SKT)

        registerInfoViewModel.requestSMS()

        printResult("repositoryTest1")

        Assert.assertEquals(LiveDataTestUtil.getValue(registerInfoViewModel.errorPopup), "Information_is_wrong")
    }

    /**
     * Collect Information
     */
    @Test
    fun repositoryTest2() {
        registerInfoViewModel.setName("john")
        registerInfoViewModel.setPersonalCode1("900927")
        registerInfoViewModel.setPersonalCode2("1")
        registerInfoViewModel.setPhone("01067423129")
        registerInfoViewModel.setProvider(MobileProvider.SKT)

//        registerInfoViewModel.requestSMS()

        var token: DRequestToken? = null

        registerInfoRepository.requestSMS(
            registerInfoViewModel.items.value!!.personalCode1,
            registerInfoViewModel.items.value!!.personalCode2,
            registerInfoViewModel.items.value!!.name,
            registerInfoViewModel.items.value!!.provider.providerCode,
            registerInfoViewModel.items.value!!.phone,
            object : RegisterInfoDataSource.CompletableCallback {
                override fun onComplete(requestToken: DRequestToken) {
                    token = requestToken
                }

                override fun onError(message: String?) {
                }
            }
        )

        printResult("repositoryTest2")
        println("name : ${token!!.token}\n")

        Assert.assertEquals(token!!.token, "PASS")
    }

    fun printResult(title: String) {
        println(title)
        println("name : ${registerInfoViewModel.items.value?.name}")
        println("personalCode1 : ${registerInfoViewModel.items.value?.personalCode1}")
        println("personalCode2 : ${registerInfoViewModel.items.value?.personalCode2}")
        println("phone : ${registerInfoViewModel.items.value?.phone}")
        println("provider : ${registerInfoViewModel.items.value?.provider}")
        println("\n")
    }
}