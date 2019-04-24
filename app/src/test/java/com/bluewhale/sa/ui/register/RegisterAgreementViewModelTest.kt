package com.bluewhale.sa.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.LiveDataTestUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class RegisterAgreementViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var registerAgreementViewModel: RegisterAgreementViewModel

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        registerAgreementViewModel = RegisterAgreementViewModel()
    }

    /**
     * clause1 : false
     * clause2 : false
     * clause3 : false
     *
     * result -> false
     */
    @Test
    fun passableTest1() {
        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true
     * clause2 : true
     * clause3 : true
     *
     * result -> true
     */
    @Test
    fun passableTest2() {
        registerAgreementViewModel.setClauseAll(true)
        Assert.assertTrue(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true -> false
     * clause2 : true -> false
     * clause3 : true -> false
     *
     * result -> false
     */
    @Test
    fun passableTest3() {
        registerAgreementViewModel.setClauseAll(true)
        registerAgreementViewModel.setClauseAll(false)
        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true
     * clause2 : true
     * clause3 : true -> false
     *
     * result -> true
     */
    @Test
    fun passableTest4() {
        registerAgreementViewModel.setClauseAll(true)
        registerAgreementViewModel.setClause3(false)
        Assert.assertTrue(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : false
     * clause2 : false
     * clause3 : false -> true
     *
     * result -> false
     */
    @Test
    fun passableTest5() {
        registerAgreementViewModel.setClauseAll(false)
        registerAgreementViewModel.setClause3(true)
        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true
     * clause2 : false
     * clause3 : false
     *
     * result -> false
     */
    @Test
    fun passableTest6() {
        registerAgreementViewModel.setClause1(true)
        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : false
     * clause2 : true
     * clause3 : false
     *
     * result -> false
     */
    @Test
    fun passableTest7() {
        registerAgreementViewModel.setClause2(true)
        Assert.assertFalse(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }

    /**
     * clause1 : true
     * clause2 : true
     * clause3 : false
     *
     * result -> true
     */
    @Test
    fun passableTest8() {
        registerAgreementViewModel.setClause1(true)
        registerAgreementViewModel.setClause2(true)
        Assert.assertTrue(LiveDataTestUtil.getValue(registerAgreementViewModel.nextButton))
    }
}