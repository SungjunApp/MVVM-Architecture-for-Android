package com.bluewhale.sa.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.LiveDataTestUtil
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.data.source.register.RegisterInfoRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RegisterInfoViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var registerInfoRepository: RegisterInfoRepository

    private lateinit var registerInfoViewModel: RegisterInfoViewModel

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        registerInfoViewModel = RegisterInfoViewModel(registerInfoRepository)
    }

}