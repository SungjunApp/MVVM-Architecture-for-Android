package com.sjsoft.app.ui.upload

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.sjsoft.app.R
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.global.SingleLiveEvent
import com.sjsoft.app.ui.BaseViewModel
import javax.inject.Inject


class UploadViewModel
@Inject constructor(private val pixleeDataSource: PixleeDataSource) : BaseViewModel() {
    //Input
    val buttonUI = MutableLiveData<Boolean>().apply { value = false }
    var title: String? = null
    fun updateTitle(text: String) {
        title = text.trim()
        buttonUI.value = title?.isNotEmpty()
    }

    //List
    sealed class ListUI {
        object LoadingShown : ListUI()
        object LoadingHidden : ListUI()
        data class Data(val list: List<S3ObjectSummary>) : ListUI()
    }
    val loadMoreUI = MutableLiveData<Boolean>().apply { value = false }
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

    //Upload
    sealed class UploadUI {
        object Loading : UploadUI()
        data class Error(@StringRes val message:Int) : UploadUI()
        data class Complete(val url: String, @StringRes val message:Int) : UploadUI()
    }
    val uploadUI = SingleLiveEvent<UploadUI>()
    fun uploadImage(filePath: String, contentType: String) {
        launchVMScope({
            uploadUI.value = UploadUI.Loading
            title?.also {
                val uploadedImageUrl = pixleeDataSource.uploadImage(it, filePath, contentType)
                uploadUI.value = UploadUI.Complete(uploadedImageUrl, R.string.upload_success_message)
            }

        }, {
            uploadUI.value = UploadUI.Error(R.string.upload_failure_message)
        })
    }
}

