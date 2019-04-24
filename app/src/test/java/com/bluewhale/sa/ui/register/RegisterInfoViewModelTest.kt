package com.bluewhale.sa.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.LiveDataTestUtil
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
    private lateinit var registerInfoRepository: RegisterInfoRepository

    private lateinit var registerInfoViewModel: RegisterInfoViewModel

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

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

        registerInfoViewModel.setProvider(RegisterInfoData.MobileProvider.SKT)
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
        registerInfoViewModel.setProvider(RegisterInfoData.MobileProvider.SKT)

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
        registerInfoViewModel.setProvider(RegisterInfoData.MobileProvider.SKT)

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
        registerInfoViewModel.setProvider(RegisterInfoData.MobileProvider.SKT)

        Assert.assertTrue(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))
    }
}