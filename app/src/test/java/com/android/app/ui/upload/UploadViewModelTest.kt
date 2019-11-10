package com.android.app.ui.upload

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.android.app.rule.CoroutinesTestRule
import com.sjsoft.app.R
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.ui.upload.UploadViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UploadViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var pixlee: PixleeDataSource

    private lateinit var viewModel: UploadViewModel

    @Mock
    private lateinit var listObserver: Observer<UploadViewModel.ListUI>

    @Captor
    private lateinit var listCaptor: ArgumentCaptor<UploadViewModel.ListUI>

    @Mock
    private lateinit var loadMoreObserver: Observer<Boolean>

    @Captor
    private lateinit var loadMoreCaptor: ArgumentCaptor<Boolean>

    @Mock
    private lateinit var buttonObserver: Observer<Boolean>

    @Captor
    private lateinit var buttonCaptor: ArgumentCaptor<Boolean>

    @Mock
    private lateinit var uploadObserver: Observer<UploadViewModel.UploadUI>

    @Captor
    private lateinit var uploadCaptor: ArgumentCaptor<UploadViewModel.UploadUI>



    @ExperimentalCoroutinesApi
    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = UploadViewModel(pixlee)
        viewModel.listUI.observeForever(listObserver)
        viewModel.loadMoreUI.observeForever(loadMoreObserver)
        viewModel.buttonUI.observeForever(buttonObserver)
        viewModel.uploadUI.observeForever(uploadObserver)
    }

    @After
    fun after() {
        viewModel.listUI.removeObserver(listObserver)
        viewModel.loadMoreUI.removeObserver(loadMoreObserver)
        viewModel.buttonUI.removeObserver(buttonObserver)
        viewModel.uploadUI.removeObserver(uploadObserver)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `set a text`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        viewModel.updateTitle("")
        viewModel.updateTitle(" ")
        viewModel.updateTitle("good morning")
        viewModel.updateTitle("hihi")
        viewModel.updateTitle("")
        viewModel.updateTitle("nice")

        verify(buttonObserver, times(7)).onChanged(buttonCaptor.capture())
        Assert.assertEquals(false, buttonCaptor.allValues[0])
        Assert.assertEquals(false, buttonCaptor.allValues[1])
        Assert.assertEquals(false, buttonCaptor.allValues[2])
        Assert.assertEquals(true, buttonCaptor.allValues[3])
        Assert.assertEquals(true, buttonCaptor.allValues[4])
        Assert.assertEquals(false, buttonCaptor.allValues[5])
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get S3Images succeeded`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val item = S3ObjectSummary()
        item.key = "hello"
        val list = listOf(
            item
        )

        `when`(pixlee.getS3Images()).thenReturn(list)
        viewModel.getS3Images()

        verify(loadMoreObserver, times(2)).onChanged(loadMoreCaptor.capture())
        Assert.assertEquals(false, loadMoreCaptor.allValues[0])
        Assert.assertEquals(false, loadMoreCaptor.allValues[1])

        verify(listObserver, times(2)).onChanged(listCaptor.capture())
        Assert.assertEquals(UploadViewModel.ListUI.LoadingShown, listCaptor.allValues[0])
        Assert.assertEquals(UploadViewModel.ListUI.Data(list), listCaptor.allValues[1])
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get S3Images failed`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        `when`(pixlee.getS3Images()).thenThrow(IllegalArgumentException(""))
        viewModel.getS3Images()

        verify(loadMoreObserver, times(3)).onChanged(loadMoreCaptor.capture())
        Assert.assertEquals(false, loadMoreCaptor.allValues[0])
        Assert.assertEquals(false, loadMoreCaptor.allValues[1])
        Assert.assertEquals(true, loadMoreCaptor.allValues[2])

        verify(listObserver, times(2)).onChanged(listCaptor.capture())
        Assert.assertEquals(UploadViewModel.ListUI.LoadingShown, listCaptor.allValues[0])
        Assert.assertEquals(UploadViewModel.ListUI.LoadingHidden, listCaptor.allValues[1])
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `upload an image succeeded`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val title = "nice"
        viewModel.updateTitle(title)

        verify(buttonObserver, times(2)).onChanged(buttonCaptor.capture())
        Assert.assertEquals(false, buttonCaptor.allValues[0])
        Assert.assertEquals(true, buttonCaptor.allValues[1])

        val filePath = "image.png"
        val contentType = "image"
        val returnUrl = "http://aws.hihi.com/face.jpg"
        `when`(pixlee.uploadImage(title, filePath, contentType)).thenReturn(returnUrl)

        viewModel.uploadImage(filePath, contentType)

        verify(uploadObserver, times(2)).onChanged(uploadCaptor.capture())
        Assert.assertEquals(UploadViewModel.UploadUI.Loading, uploadCaptor.allValues[0])
        Assert.assertEquals(UploadViewModel.UploadUI.Complete(returnUrl, R.string.upload_success_message), uploadCaptor.allValues[1])
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `upload an image failed`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val title = "nice"
        viewModel.updateTitle(title)

        verify(buttonObserver, times(2)).onChanged(buttonCaptor.capture())
        Assert.assertEquals(false, buttonCaptor.allValues[0])
        Assert.assertEquals(true, buttonCaptor.allValues[1])

        val filePath = "image.png"
        val contentType = "image"
        `when`(pixlee.uploadImage(title, filePath, contentType)).thenThrow(IllegalArgumentException(""))

        viewModel.uploadImage(filePath, contentType)

        verify(uploadObserver, times(2)).onChanged(uploadCaptor.capture())
        Assert.assertEquals(UploadViewModel.UploadUI.Loading, uploadCaptor.allValues[0])
        Assert.assertEquals(UploadViewModel.UploadUI.Error(R.string.upload_failure_message), uploadCaptor.allValues[1])
    }

}