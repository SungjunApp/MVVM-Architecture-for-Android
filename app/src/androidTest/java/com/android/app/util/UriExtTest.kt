package com.android.app.util

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.util.queryDeepLinkParam
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UriExtTest {
    @Test
    fun quryDeepLinkParam1() {
        val uri: Uri = Uri.parse("lotto://sungjunapp?drwNo=1")
        val drwNo = uri.queryDeepLinkParam(AppConfig.DeepLinkParams.drwNo)
        assertEquals("1", drwNo)
    }

    @Test
    fun quryDeepLinkParam2() {
        val uri: Uri = Uri.parse("https://sungjunapp.com?drwNo=39")
        val drwNo = uri.queryDeepLinkParam(AppConfig.DeepLinkParams.drwNo)
        assertEquals("39", drwNo)
    }

    @Test
    fun quryDeepLinkParam3() {
        val uri: Uri = Uri.parse("https://sungjunapp.com?drwNo=39")
        val drwNo = uri.queryDeepLinkParam(AppConfig.DeepLinkParams.drwNo)
        assertEquals("39", drwNo)
    }
}
