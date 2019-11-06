package com.android.app.ui.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.app.rule.CoroutinesTestRule
import com.android.app.util.LottoTestUtil
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.room.Lotto
import com.sjsoft.app.ui.gallery.GalleryViewModel
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
class HistoryViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: LottoDataSource

    private lateinit var viewModel: GalleryViewModel

    @Mock
    private lateinit var observer: Observer<List<Lotto>>

    @Captor
    private lateinit var captor: ArgumentCaptor<List<Lotto>>

    @ExperimentalCoroutinesApi
    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = GalleryViewModel(api)
        viewModel.list.observeForever(observer)
    }

    @After
    fun after() {
        viewModel.list.removeObserver(observer)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getHistories`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val item = listOf(
            LottoTestUtil.makeLotto(3)
        )
        `when`(api.loadAllLottos()).thenReturn(item)

        viewModel.getList()

        verify(observer).onChanged(captor.capture())
        Assert.assertEquals(
            item,
            captor.value
        )
    }

}