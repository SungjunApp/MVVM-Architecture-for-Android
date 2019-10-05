package com.android.app.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.app.rule.CoroutinesTestRule
import com.android.app.util.ExceptionTest
import com.android.app.util.LottoTestUtil
import com.sjsoft.app.R
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.mockito.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class SearchViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: LottoDataSource

    private lateinit var viewModel: SearchViewModel

    @Mock
    private lateinit var observer: Observer<SearchViewModel.LottoUI>

    @Captor
    private lateinit var captor: ArgumentCaptor<SearchViewModel.LottoUI>

    @Mock
    private lateinit var errorObserver: Observer<Int>

    @Captor
    private lateinit var errorCaptor: ArgumentCaptor<Int>

    @ExperimentalCoroutinesApi
    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchViewModel(api)
        viewModel.lottoUI.observeForever(observer)
        viewModel.errorPopup.observeForever(errorObserver)
    }

    @After
    fun after() {
        viewModel.lottoUI.removeObserver(observer)
        viewModel.errorPopup.observeForever(errorObserver)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getWinner`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val drwNo = 49
        val item = LottoTestUtil.makeLotto(drwNo)
        `when`(api.getWinder(drwNo)).thenReturn(item)

        viewModel.getWinner(drwNo)
        advanceTimeBy(AppConfig.enteringDelay)

        verify(observer, Mockito.times(2)).onChanged(captor.capture())

        Assert.assertEquals(SearchViewModel.LottoUI.Loading(true), captor.allValues[0])
        Assert.assertEquals(SearchViewModel.LottoUI.Data(item), captor.allValues[1])
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `saerch http exception`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val drwNo = 49
        `when`(api.getWinder(drwNo)).thenThrow(ExceptionTest.getHttpException())

        viewModel.getWinner(drwNo)
        advanceTimeBy(AppConfig.enteringDelay)

        verify(observer, Mockito.times(2)).onChanged(captor.capture())

        Assert.assertEquals(SearchViewModel.LottoUI.Loading(true), captor.allValues[0])
        Assert.assertEquals(SearchViewModel.LottoUI.Failur, captor.allValues[1])

        verify(errorObserver, Mockito.times(1)).onChanged(errorCaptor.capture())
        Assert.assertEquals(R.string.error_code, errorCaptor.allValues[0])
    }
}