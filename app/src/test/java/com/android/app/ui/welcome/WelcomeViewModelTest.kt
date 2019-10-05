package com.android.app.ui.welcome

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.ui.welcome.WelcomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class WelcomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockCommandObserver: Observer<Int>

    @Captor
    private lateinit var uiCommandCaptor: ArgumentCaptor<Int>

    @Mock
    lateinit var pref: PreferenceDataSource

    private lateinit var viewModel: WelcomeViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = WelcomeViewModel(pref)

        viewModel.command.observeForever(mockCommandObserver)
    }

    @After
    fun after() {
        viewModel.command.removeObserver(mockCommandObserver)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `goToMain`() {
        viewModel.start()
        verify(mockCommandObserver).onChanged(uiCommandCaptor.capture())
        Assert.assertEquals(1, uiCommandCaptor.value)
    }
}