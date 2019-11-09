package com.android.app.ui.gallery

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.app.rule.CoroutinesTestRule
import com.android.app.util.PixleeTestUtil
import com.pixlee.pixleesdk.PXLAlbumSortType
import com.sjsoft.app.data.PXLPhotoItem
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.ui.gallery.GalleryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GalleryViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var pixlee: PixleeDataSource

    private lateinit var viewModel: GalleryViewModel

    @Mock
    private lateinit var listObserver: Observer<GalleryViewModel.ListUI>

    @Captor
    private lateinit var listCaptor: ArgumentCaptor<GalleryViewModel.ListUI>

    @Mock
    private lateinit var loadMoreObserver: Observer<Boolean>

    @Captor
    private lateinit var loadMoreCaptor: ArgumentCaptor<Boolean>

    @Mock
    private lateinit var sortTypeObserver: Observer<PXLAlbumSortType>

    @Captor
    private lateinit var sortTypeCaptor: ArgumentCaptor<PXLAlbumSortType>

    @ExperimentalCoroutinesApi
    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = GalleryViewModel(pixlee)
        viewModel.listUI.observeForever(listObserver)
        viewModel.loadMoreUI.observeForever(loadMoreObserver)
        viewModel.sortType.observeForever(sortTypeObserver)
    }

    @After
    fun after() {
        viewModel.listUI.removeObserver(listObserver)
        viewModel.loadMoreUI.removeObserver(loadMoreObserver)
        viewModel.sortType.removeObserver(sortTypeObserver)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `loadList`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val items = PixleeTestUtil.getPhotoItems()
        fun getFlowPhotoItems(): Flow<ArrayList<PXLPhotoItem>> = flow {
            emit(items)
        }

        val type = PXLAlbumSortType.RECENCY
        val options = viewModel.generateSortOption(type)

        `when`(pixlee.loadNextPageOfPhotos(options)).thenReturn(getFlowPhotoItems())

        viewModel.changeTab(type)

        verify(sortTypeObserver, times(1)).onChanged(sortTypeCaptor.capture())
        Assert.assertEquals(
            type,
            sortTypeCaptor.allValues[0]
        )

        verify(listObserver, times(2)).onChanged(listCaptor.capture())
        Assert.assertEquals(
            GalleryViewModel.ListUI.LoadingShown,
            listCaptor.allValues[0]
        )
        Assert.assertEquals(
            GalleryViewModel.ListUI.Data(items),
//            GalleryViewModel.ListUI.LoadingHide,
            listCaptor.allValues[1]
        )
    }

}