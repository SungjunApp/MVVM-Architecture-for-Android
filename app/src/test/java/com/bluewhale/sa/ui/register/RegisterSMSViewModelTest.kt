package com.bluewhale.sa.ui.register

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.LiveDataTestUtil
import com.bluewhale.sa.R
import com.bluewhale.sa.data.FakeRegisterRepository
import com.bluewhale.sa.data.FakeRegisterRepository.Companion.testToken
import com.bluewhale.sa.data.source.register.DRequestToken
import com.libs.meuuslibs.network.FakeBaseRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException

class RegisterSMSViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var navigator: RegisterNavigator

    @Mock
    private lateinit var application: Application

    //@Mock
    private lateinit var registerRepository: FakeRegisterRepository

    private lateinit var registerSMSViewModel: RegisterSMSViewModel

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        registerRepository = FakeRegisterRepository()

        // Get a reference to the class under test
        registerSMSViewModel = RegisterSMSViewModel(navigator, registerRepository, false, DRequestToken(testToken))
    }

    /**
     * default
     *
     * nextButton -> false
     */
    @Test
    fun passableTest1() {
        printNextButton("passableTest1")

        Assert.assertFalse(LiveDataTestUtil.getValue(registerSMSViewModel.nextButton))
    }

    /**
     * authCode : null -> "test"
     *
     * nextButton -> false
     */
    @Test
    fun passableTest2() {
        registerSMSViewModel.setAuthCode("test")

        printNextButton("passableTest2")
        Assert.assertFalse(LiveDataTestUtil.getValue(registerSMSViewModel.nextButton))
    }

    /**
     * default
     *
     * token : DRequestToken(testToken)
     * marketingClause : false
     * authCode : null
     *
     * nextButton -> false
     *
     * response -> error(R.string.ERROR_NICE_INVALID_TOKEN)
     */
    @Test
    fun authTest1() {
        registerSMSViewModel = RegisterSMSViewModel(navigator, registerRepository, false, DRequestToken(testToken))

        Assert.assertFalse(LiveDataTestUtil.getValue(registerSMSViewModel.nextButton))

        val testObserver = registerSMSViewModel.verifyCode().test()
        printAuthCallback("authTest1")
        println("nextButton : ${registerSMSViewModel.nextButton.value}\n")

        testObserver.assertNotComplete()
//        Assert.assertEquals(LiveDataTestUtil.getValue(registerSMSViewModel.errorPopup), R.string.ERROR_NICE_AUTH_FAILED)
    }

    /**
     * Wrong Token
     *
     * token : DRequestToken(testToken)
     * marketingClause : false
     * authCode : "test"
     *
     * nextButton -> false
     *
     * response -> error(R.string.ERROR_NICE_INVALID_TOKEN)
     */
    @Test
    fun authTest2() {
        registerSMSViewModel = RegisterSMSViewModel(navigator, registerRepository, false, DRequestToken(testToken))

        registerSMSViewModel.setAuthCode("test")
        Assert.assertFalse(LiveDataTestUtil.getValue(registerSMSViewModel.nextButton))

        val testObserver = registerSMSViewModel.verifyCode().test()
        printAuthCallback("authTest2")
        println("nextButton : ${registerSMSViewModel.nextButton.value}\n")

        testObserver.assertNotComplete()
//        Assert.assertEquals(LiveDataTestUtil.getValue(registerSMSViewModel.errorPopup), R.string.ERROR_NICE_AUTH_FAILED)
    }

    /**
     * Collect Token
     *
     * token : DRequestToken(testToken)
     * marketingClause : false
     * authCode : "test00"
     *
     * nextButton -> true
     *
     * response -> complete(FakeRegisterSMSDataSource.testUser)
     */
    @Test
    fun authTest3() {
        registerSMSViewModel = RegisterSMSViewModel(navigator, registerRepository, false, DRequestToken(testToken))

        registerSMSViewModel.setAuthCode("test00")
        Assert.assertTrue(LiveDataTestUtil.getValue(registerSMSViewModel.nextButton))

        val testObserver = registerSMSViewModel.verifyCode().test()
        printAuthCallback("authTest3")
        println("nextButton : ${registerSMSViewModel.nextButton.value}\n")

        testObserver.assertComplete()
    }


    fun printNextButton(title: String) {
        println(title)
        println("nextButton : ${registerSMSViewModel.nextButton.value}")
        println("\n")
    }

    fun printAuthCallback(title: String) {
        println(title)
        println("token : ${registerSMSViewModel.requestToken.token}")
        println("marketingClause : ${registerSMSViewModel.marketingClause}")
        println("authCode : ${registerSMSViewModel.authCode.value}")
        println("\n")
    }
}