package com.bluewhale.sa.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.LiveDataTestUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RegisterAgreementViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var registerAgreementViewModel: RegisterAgreementViewModel

    @Mock
    private lateinit var navigator: RegisterNavigator

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        registerAgreementViewModel = RegisterAgreementViewModel(navigator)
    }

    /**
     * default
     *
     * clause1 : false
     * clause2 : false
     * clause3 : false
     *
     * nextButton -> false
     */
    @Test
    fun passableTest1() {
        printResult("passableTest1")

        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true
     * clause2 : true
     * clause3 : true
     *
     * nextButton -> true
     */
    @Test
    fun passableTest2() {
        registerAgreementViewModel.setClauseAll(true)

        printResult("passableTest2")

        Assert.assertTrue(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true -> false
     * clause2 : true -> false
     * clause3 : true -> false
     *
     * nextButton -> false
     */
    @Test
    fun passableTest3() {
        registerAgreementViewModel.setClauseAll(true)
        registerAgreementViewModel.setClauseAll(false)

        printResult("passableTest3")

        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true
     * clause2 : true
     * clause3 : true -> false
     *
     * nextButton -> true
     */
    @Test
    fun passableTest4() {
        registerAgreementViewModel.setClauseAll(true)
        registerAgreementViewModel.setClause3(false)

        printResult("passableTest4")

        Assert.assertTrue(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : false
     * clause2 : false
     * clause3 : false -> true
     *
     * nextButton -> false
     */
    @Test
    fun passableTest5() {
        registerAgreementViewModel.setClauseAll(false)
        registerAgreementViewModel.setClause3(true)

        printResult("passableTest5")

        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true
     * clause2 : false
     * clause3 : false
     *
     * nextButton -> false
     */
    @Test
    fun passableTest6() {
        registerAgreementViewModel.setClause1(true)

        printResult("passableTest6")

        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : false
     * clause2 : true
     * clause3 : false
     *
     * nextButton -> false
     */
    @Test
    fun passableTest7() {
        registerAgreementViewModel.setClause2(true)

        printResult("passableTest7")

        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true
     * clause2 : true
     * clause3 : false
     *
     * nextButton -> true
     */
    @Test
    fun passableTest8() {
        registerAgreementViewModel.setClause1(true)
        registerAgreementViewModel.setClause2(true)

        printResult("passableTest8")

        Assert.assertTrue(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true -> false
     * clause2 : true -> false
     * clause3 : false
     *
     * nextButton -> false
     */
    @Test
    fun passableTest9() {
        registerAgreementViewModel.setClause1(true)
        registerAgreementViewModel.setClause2(true)
        registerAgreementViewModel.setClauseAll(false)

        printResult("passableTest9")

        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    fun printResult(title: String) {
        println(title)
        println("clause1 : ${registerAgreementViewModel.items.value?.clause1}")
        println("clause2 : ${registerAgreementViewModel.items.value?.clause2}")
        println("clause3 : ${registerAgreementViewModel.items.value?.clause3}")
        println("\n")
    }
}