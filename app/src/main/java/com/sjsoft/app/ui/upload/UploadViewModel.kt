package com.sjsoft.app.ui.upload

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.ui.BaseViewModel
import com.sjsoft.app.ui.gallery.GalleryViewModel
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

    val loadMoreUI = MutableLiveData<Boolean>().apply { value = false }

    sealed class ListUI{
        object LoadingShown:ListUI()
        object LoadingHidden:ListUI()
        data class Data(val list: List<S3ObjectSummary>):ListUI()
    }
    val listUI = MutableLiveData<ListUI>()
    fun getS3Images() {
        launchVMScope({
            loadMoreUI.value = false
            listUI.value = ListUI.LoadingShown
            val list = pixleeDataSource.getS3Images()
            listUI.value = ListUI.Data(list)
            list.forEach {
                Log.e("UploadVM", "UploadVM.it.key: ${it.key}")

            }
        }, {
            loadMoreUI.value = true
            listUI.value = ListUI.LoadingHidden
        })
    }
}

