package com.bluewhale.sa.ui.register

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.Injection.provideRegisterSMSRepository
import com.bluewhale.sa.data.FakeRegisterSMSDataSource.Companion.testToken
import com.bluewhale.sa.data.source.register.DRequestToken
import com.bluewhale.sa.data.source.register.RegisterSMSRepository
import org.junit.Before
import org.junit.Rule
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
     * name : null
     * personalCode1 : null
     * personalCode2 : null
     * phone : null
     * provider : UNSELECTED
     *
     * nextButton -> false
     */
//    @Test
//    fun passableTest1() {
//        printResult("passableTest1")
//
//        Assert.assertFalse(LiveDataTestUtil.getValue(registerInfoViewModel.nextButton))
//    }
//
//    fun printResult(title: String) {
//        println(title)
//        println("name : ${registerInfoViewModel.items.value?.name}")
//        println("personalCode1 : ${registerInfoViewModel.items.value?.personalCode1}")
//        println("personalCode2 : ${registerInfoViewModel.items.value?.personalCode2}")
//        println("phone : ${registerInfoViewModel.items.value?.phone}")
//        println("provider : ${registerInfoViewModel.items.value?.provider}")
//        println("\n")
//    }
}