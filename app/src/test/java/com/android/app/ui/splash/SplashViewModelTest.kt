package com.android.app.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.app.rule.CoroutinesTestRule
import com.android.app.util.ExceptionTest
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.ui.main.SplashViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SplashViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockUIDataObserver: Observer<SplashViewModel.UIData>

    @Captor
    private lateinit var uiDataCaptor: ArgumentCaptor<SplashViewModel.UIData>

    @Mock
    lateinit var api: LottoDataSource

    @Mock
    lateinit var pref: PreferenceDataSource

    private lateinit var viewModel: SplashViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = SplashViewModel(api, pref)

        viewModel.uiData.observeForever(mockUIDataObserver)
    }

    @After
    fun after() {
        viewModel.uiData.removeObserver(mockUIDataObserver)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `goToMain`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        `when`(pref.getReadWelcome()).thenReturn(true)

        viewModel.syncData()
        advanceTimeBy(AppConfig.splashDelay)

        verify(mockUIDataObserver, times(2)).onChanged(uiDataCaptor.capture())

        Assert.assertEquals(SplashViewModel.UIData.Loading, uiDataCaptor.allValues[0])
        Assert.assertEquals(SplashViewModel.UIData.Main, uiDataCaptor.allValues[1])
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `goToWelcome`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        `when`(pref.getReadWelcome()).thenReturn(true)

        viewModel.syncData()
        advanceTimeBy(AppConfig.splashDelay)

        verify(mockUIDataObserver, times(2)).onChanged(uiDataCaptor.capture())

        Assert.assertEquals(SplashViewModel.UIData.Loading, uiDataCaptor.allValues[0])
        Assert.assertEquals(SplashViewModel.UIData.Main, uiDataCaptor.allValues[1])
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `splash http exception`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        `when`(pref.getReadWelcome()).thenReturn(true)

        `when`(api.syncWinnersInfo(1, 50)).thenThrow(ExceptionTest.getHttpException())

        viewModel.syncData()

        verify(mockUIDataObserver, times(1)).onChanged(uiDataCaptor.capture())

        //Assert.assertEquals(SplashViewModel.UIData.Loading, uiDataCaptor.allValues[0])
        Assert.assertEquals(SplashViewModel.UIData.Error, uiDataCaptor.allValues[0])
    }
}