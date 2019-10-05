package com.android.app.ui.home

import androidx.annotation.StringRes
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.app.rule.CoroutinesTestRule
import com.android.app.util.ExceptionTest
import com.android.app.util.LottoTestUtil
import com.sjsoft.app.R
import com.sjsoft.app.data.LottoMatch
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: LottoDataSource

    @Mock
    lateinit var pref: PreferenceDataSource

    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var lottoObserver: Observer<String>

    @Captor
    private lateinit var lottoCaptor: ArgumentCaptor<String>

    @Mock
    private lateinit var matchUIObserver: Observer<HomeViewModel.LottoMatchUI>

    @Captor
    private lateinit var matchUICaptor: ArgumentCaptor<HomeViewModel.LottoMatchUI>

    @Mock
    private lateinit var errorObserver: Observer<Int>

    @Captor
    private lateinit var errorCaptor: ArgumentCaptor<Int>

    @ExperimentalCoroutinesApi
    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(api, pref)

        viewModel.lotto.observeForever(lottoObserver)
        viewModel.lottoMatchUI.observeForever(matchUIObserver)
        viewModel.errorPopup.observeForever(errorObserver)

    }

    @After
    fun after() {
        viewModel.lotto.removeObserver(lottoObserver)
        viewModel.lottoMatchUI.removeObserver(matchUIObserver)
        viewModel.errorPopup.removeObserver(errorObserver)
    }

    val eventNumber = "10"

    @ExperimentalCoroutinesApi
    @Test
    fun `generate lotto`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        //Generate My Lotto
        generateMyLotto()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `searching LottoMath success`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        //Generate My Lotto
        generateMyLotto()

        //Match mine with the  official Lotto Numbers
        val lotto = LottoTestUtil.makeLotto(eventNumber.toInt())
        `when`(api.getWinder(eventNumber.toInt())).thenReturn(lotto)

        viewModel.matchMineWithOfficial()

        verify(matchUIObserver, times(2)).onChanged(matchUICaptor.capture())
        Assert.assertEquals(
            HomeViewModel.LottoMatchUI.Loading(true),
            matchUICaptor.allValues[0]
        )
        Assert.assertEquals(
            HomeViewModel.LottoMatchUI.Data(
                LottoMatch(
                    lotto,
                    viewModel.generatedLotto!!
                )
            ),
            matchUICaptor.allValues[1]
        )


    }

    @ExperimentalCoroutinesApi
    @Test
    fun `searching LottoMatch leads to HTTPException`() {
        occurException(ExceptionTest.getHttpException(), R.string.error_code)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `searching LottoMatch leads to IllegalArgumentException`() {
        occurException(ExceptionTest.getIllegalArgumentException(), R.string.error_argument)
    }

    private fun occurException(exception: Exception, @StringRes errorString: Int) =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            //Generate My Lotto
            generateMyLotto()

            //Match mine with the  official Lotto Numbers
            `when`(api.getWinder(eventNumber.toInt())).thenThrow(exception)

            viewModel.matchMineWithOfficial()

            verify(matchUIObserver, times(2)).onChanged(matchUICaptor.capture())
            Assert.assertEquals(
                HomeViewModel.LottoMatchUI.Loading(true),
                matchUICaptor.allValues[0]
            )
            Assert.assertEquals(
                HomeViewModel.LottoMatchUI.Loading(false),
                matchUICaptor.allValues[1]
            )

            verify(errorObserver, times(1)).onChanged(errorCaptor.capture())
            Assert.assertEquals(errorString, errorCaptor.allValues[0])
        }

    private fun generateMyLotto() {
        //Generate My Lotto
        viewModel.eventNumber = eventNumber
        viewModel.generateLotto()
        val generatedLotto = viewModel.generatedLotto
        verify(lottoObserver, times(2)).onChanged(lottoCaptor.capture())
        Assert.assertEquals("", lottoCaptor.allValues[0])
        Assert.assertEquals(generatedLotto!!.displayText, lottoCaptor.allValues[1])
    }


}