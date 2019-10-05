package com.android.app.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.data.repository.PreferenceRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@LargeTest
class PreferenceRepositoryTest {
    lateinit var repo: PreferenceDataSource

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        repo = PreferenceRepository(context)
    }

    @After
    @Throws(IOException::class)
    fun after() {
    }

    @Test
    @Throws(Exception::class)
    fun testPreference() = runBlocking {
        repo.setReadWelcome(true)
        assertEquals(
            repo.getReadWelcome(),
            true
        )

        repo.setReadWelcome(false)
        assertEquals(
            repo.getReadWelcome(),
            false
        )
    }
}