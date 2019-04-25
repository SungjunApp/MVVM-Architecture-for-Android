package com.bluewhale.sa.ui.register

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.Injection.provideRegisterSMSRepository
import com.bluewhale.sa.LiveDataTestUtil
import com.bluewhale.sa.R
import com.bluewhale.sa.data.FakeRegisterSMSDataSource
import com.bluewhale.sa.data.FakeRegisterSMSDataSource.Companion.testToken
import com.bluewhale.sa.data.source.register.DRequestToken
import com.bluewhale.sa.data.source.register.DUser
import com.bluewhale.sa.data.source.register.RegisterSMSDataSource
import com.bluewhale.sa.data.source.register.RegisterSMSRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RegisterSMSViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var navigator: RegisterNavigator

    @Mock
    private lateinit var application: Application

    //@Mock
    private lateinit var registerSMSRepository: RegisterSMSRepository

    private lateinit var registerSMSViewModel: RegisterSMSViewModel

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        registerSMSRepository = provideRegisterSMSRepository(application)

        // Get a reference to the class under test
        registerSMSViewModel = RegisterSMSViewModel(navigator, registerSMSRepository, false, DRequestToken(testToken))
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
     * token : DRequestToken(testToken)
     * marketingClause : false
     * authCode : null
     *
     * nextButton -> false
     *
     * callback -> error(R.string.register_invalid_information)
     */
    @Test
    fun authTest1() {
        registerSMSViewModel = RegisterSMSViewModel(navigator, registerSMSRepository, false, DRequestToken(testToken))

        Assert.assertFalse(LiveDataTestUtil.getValue(registerSMSViewModel.nextButton))

        registerSMSViewModel.verifyCode()

        printAuthCallback("authTest1")

        println("nextButton : ${registerSMSViewModel.nextButton.value}\n")
        Assert.assertEquals(
            LiveDataTestUtil.getValue(registerSMSViewModel.errorPopup),
            R.string.register_invalid_information
        )
    }

    /**
     * token : DRequestToken(testToken)
     * marketingClause : false
     * authCode : "test"
     *
     * nextButton -> false
     *
     * callback -> error(R.string.register_invalid_information)
     */
    @Test
    fun authTest2() {
        registerSMSViewModel = RegisterSMSViewModel(navigator, registerSMSRepository, false, DRequestToken(testToken))

        registerSMSViewModel.setAuthCode("test")
        Assert.assertFalse(LiveDataTestUtil.getValue(registerSMSViewModel.nextButton))

        registerSMSViewModel.verifyCode()

        printAuthCallback("authTest2")

        println("nextButton : ${registerSMSViewModel.nextButton.value}\n")
        Assert.assertEquals(
            LiveDataTestUtil.getValue(registerSMSViewModel.errorPopup),
            R.string.register_invalid_information
        )
    }

    /**
     * token : DRequestToken(testToken)
     * marketingClause : false
     * authCode : "test00"
     *
     * nextButton -> true
     *
     * callback -> complete(FakeRegisterSMSDataSource.testUser)
     */
    @Test
    fun authTest3() {
        registerSMSViewModel = RegisterSMSViewModel(navigator, registerSMSRepository, false, DRequestToken(testToken))

        registerSMSViewModel.setAuthCode("test00")
        Assert.assertTrue(LiveDataTestUtil.getValue(registerSMSViewModel.nextButton))

        var testUser: DUser? = null
        registerSMSRepository.verifyCode(
            registerSMSViewModel.requestToken.token,
            registerSMSViewModel.marketingClause,
            registerSMSViewModel.authCode.value!!,
            object : RegisterSMSDataSource.CompletableCallback {
                override fun onComplete(dUser: DUser) {
                    testUser = dUser
                }

                override fun onError(message: Int) {
                }
            }
        )


        printAuthCallback("authTest1")

        println("nextButton : ${registerSMSViewModel.nextButton.value}\n")
        Assert.assertEquals(
            testUser,
            FakeRegisterSMSDataSource.testUser
        )
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