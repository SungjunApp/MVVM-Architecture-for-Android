package com.sjsoft.app.ui.history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pixlee.pixleesdk.PXLPhoto
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


class GalleryViewModel
@Inject constructor(private val pixleeDataSource: PixleeDataSource) : BaseViewModel() {
    val listUI: MutableLiveData<UIData> = MutableLiveData()

    sealed class UIData {
        object Loading : UIData()
        class Data(val list:List<PXLPhoto>):UIData()
        object Error : UIData()
    }

    fun getFirstList() {
        launchVMScope({
            listUI.value = UIData.Loading

            pixleeDataSource.loadNextPageOfPhotos().collect {
                listUI.value = UIData.Data(it)
            }
        }, {
            listUI.value = UIData.Error
        })
    }
}