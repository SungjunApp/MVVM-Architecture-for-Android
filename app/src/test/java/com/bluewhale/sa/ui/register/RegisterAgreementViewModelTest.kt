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

    private lateinit var mViewModel: RegisterAgreementViewModel

    @Mock
    private lateinit var mNavigator: RegisterNavigator

    @Before
    fun setupViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        mViewModel = RegisterAgreementViewModel(mNavigator)
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

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
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
        mViewModel.setClauseAll(true)

        printResult("passableTest2")

        Assert.assertTrue(LiveDataTestUtil.getValue(mViewModel.nextButton))
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
        mViewModel.setClauseAll(true)
        mViewModel.setClauseAll(false)

        printResult("passableTest3")

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
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
        mViewModel.setClauseAll(true)
        mViewModel.setClause3(false)

        printResult("passableTest4")

        Assert.assertTrue(LiveDataTestUtil.getValue(mViewModel.nextButton))
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
        mViewModel.setClauseAll(false)
        mViewModel.setClause3(true)

        printResult("passableTest5")

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
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
        mViewModel.setClause1(true)

        printResult("passableTest6")

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
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
        mViewModel.setClause2(true)

        printResult("passableTest7")

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
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
        mViewModel.setClause1(true)
        mViewModel.setClause2(true)

        printResult("passableTest8")

        Assert.assertTrue(LiveDataTestUtil.getValue(mViewModel.nextButton))
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
        mViewModel.setClause1(true)
        mViewModel.setClause2(true)
        mViewModel.setClauseAll(false)

        printResult("passableTest9")

        Assert.assertFalse(LiveDataTestUtil.getValue(mViewModel.nextButton))
    }

    fun printResult(title: String) {
        println(title)
        println("clause1 : ${mViewModel.items.value?.clause1}")
        println("clause2 : ${mViewModel.items.value?.clause2}")
        println("clause3 : ${mViewModel.items.value?.clause3}")
        println("\n")
    }
}