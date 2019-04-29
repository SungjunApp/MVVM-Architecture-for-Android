package com.bluewhale.sa.ui.register

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.Injection
import com.bluewhale.sa.LiveDataTestUtil
import com.bluewhale.sa.data.FakeRegisterRepository.Companion.testToken
import com.bluewhale.sa.data.source.register.DRequestToken
import com.example.demo.network.APIRegister
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RegisterSMSViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mViewModel: RegisterSMSViewModel

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
        mViewModel = RegisterSMSViewModel(mNavigator, mRepository, false, DRequestToken(testToken))
    }

    /**
     * default
     *
     * nextButton -> false
     */
    @Test
    fun passableTest1() {
        printNextButton("passableTest1")

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
    }

    /**
     * authCode : null -> "test"
     *
     * nextButton -> false
     */
    @Test
    fun passableTest2() {
        mViewModel.setAuthCode("test")

        printNextButton("passableTest2")
        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
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
        mViewModel = RegisterSMSViewModel(mNavigator, mRepository, false, DRequestToken(testToken))

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))

        val testObserver = mViewModel.verifyCode().test()
        printAuthCallback("authTest1")
        println("nextButton : ${mViewModel.nextButton.value}\n")

        testObserver.assertNotComplete()
//        Assert.assertEquals(LiveDataTestUtil.getValue(mViewModel.errorPopup), R.string.ERROR_NICE_AUTH_FAILED)
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
        mViewModel = RegisterSMSViewModel(mNavigator, mRepository, false, DRequestToken(testToken))

        mViewModel.setAuthCode("test")
        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))

        val testObserver = mViewModel.verifyCode().test()
        printAuthCallback("authTest2")
        println("nextButton : ${mViewModel.nextButton.value}\n")

        testObserver.assertNotComplete()
//        Assert.assertEquals(LiveDataTestUtil.getValue(mViewModel.errorPopup), R.string.ERROR_NICE_AUTH_FAILED)
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
        mViewModel = RegisterSMSViewModel(mNavigator, mRepository, false, DRequestToken(testToken))

        mViewModel.setAuthCode("test00")
        Assert.assertTrue(LiveDataTestUtil.getValue(mViewModel.nextButton))

        val testObserver = mViewModel.verifyCode().test()
        printAuthCallback("authTest3")
        println("nextButton : ${mViewModel.nextButton.value}\n")

        testObserver.assertComplete()
    }


    fun printNextButton(title: String) {
        println(title)
        println("nextButton : ${mViewModel.nextButton.value}")
        println("\n")
    }

    fun printAuthCallback(title: String) {
        println(title)
        println("token : ${mViewModel.requestToken.token}")
        println("marketingClause : ${mViewModel.marketingClause}")
        println("authCode : ${mViewModel.authCode.value}")
        println("\n")
    }
}