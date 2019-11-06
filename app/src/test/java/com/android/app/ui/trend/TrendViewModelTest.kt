package com.android.app.ui.trend

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.app.rule.CoroutinesTestRule
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.room.Frequency
import com.sjsoft.app.ui.trend.TrendViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TrendViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: LottoDataSource

    private lateinit var viewModel: TrendViewModel

    @Mock
    private lateinit var listObserver: Observer<List<Frequency>>

    @Captor
    private lateinit var listCaptor: ArgumentCaptor<List<Frequency>>

    @ExperimentalCoroutinesApi
    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = TrendViewModel(api)
        viewModel.list.observeForever(listObserver)
    }

    @After
    fun after() {
        viewModel.list.removeObserver(listObserver)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getFrequencies`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val item = listOf(
            Frequency(3,49)
        )
        `when`(api.loadAllFrequency()).thenReturn(item)

        viewModel.getList()
        advanceTimeBy(AppConfig.enteringDelay)

        verify(listObserver).onChanged(listCaptor.capture())
        Assert.assertEquals(
            item,
            listCaptor.value
        )
    }
}