package com.sjsoft.app.ui.upload

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.sjsoft.app.R
import com.sjsoft.app.data.S3Item
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.global.SingleLiveEvent
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


class UploadViewModel
@Inject constructor(private val pixlee: PixleeDataSource, private val pref: PreferenceDataSource) : BaseViewModel() {
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
        data class Data(val list: List<S3Item>) : ListUI()
    }

    val loadMoreUI = MutableLiveData<Boolean>().apply { value = false }
    val listUI = MutableLiveData<ListUI>()
    fun getS3Images() {
        launchVMScope({
            loadMoreUI.value = false
            listUI.value = ListUI.LoadingShown
            val list = pixlee.getS3Images()
            listUI.value = ListUI.Data(list)


            list.forEach {
                Log.e("UploadVM", "UploadVM.it.key: ${it.s3Object.key}")
            }
        }, {
            loadMoreUI.value = true
            listUI.value = ListUI.LoadingHidden
        })
    }

    //Upload
    sealed class UploadUI {
        data class Uploadinging(val isComplete:Boolean, val progress: Int) : UploadUI()
        data class Error(@StringRes val message: Int) : UploadUI()
        data class Complete(val url: String, @StringRes val message: Int) : UploadUI()
    }

    val uploadUI = SingleLiveEvent<UploadUI>()
    fun uploadImage(filePath: String, contentType: String) {
        launchVMScope({
            uploadUI.value = UploadUI.Uploadinging(false, 0)
            title?.also {
                pixlee.uploadImage(it, filePath, contentType).collect {
                    if (it.isComplete && it.url != null) {
                        uploadUI.value = UploadUI.Complete(it.url, R.string.upload_success_message)
                    } else {
                        uploadUI.value = UploadUI.Uploadinging(it.isComplete, it.progress)
                    }
                }

            }

        }, {
            uploadUI.value = UploadUI.Error(R.string.upload_failure_message)
        })
    }

    fun isListTapped():Boolean{
        return pref.isGalleryListTapped()
    }

    fun makeListTapped(){
        pref.makeGalleryListTapped()
    }
}

