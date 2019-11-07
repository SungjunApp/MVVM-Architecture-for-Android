package com.sjsoft.app.ui.upload

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.global.SingleLiveEvent
import com.sjsoft.app.util.NetworkErrorUtil
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.room.Lotto
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import javax.inject.Inject


class UploadViewModel
@Inject constructor(private val pixleeDataSource: PixleeDataSource) : BaseViewModel() {
    fun uploadImage(filePath: String, contentType: String) {
        launchVMScope({
            title?.also {
                val key = pixleeDataSource.uploadImage(it, filePath, contentType)
                Log.e("UploadVM", "UploadVM.uploadImage.key: ${key}")
            }

        }, {

        })
    }

    val buttonUI = MutableLiveData<Boolean>().apply { value = false }

    var title: String? = null
    fun updateTitle(text: String) {
        title = text
        buttonUI.value = text.isNotEmpty()
    }

    fun getS3Images() {
        launchVMScope({
            val list = pixleeDataSource.getS3Images()
            list.forEach {
                Log.e("UploadVM", "UploadVM.it.key: ${it.key}")

            }
        }, {})
    }
}

