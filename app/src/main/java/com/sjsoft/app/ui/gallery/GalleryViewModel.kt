package com.sjsoft.app.ui.gallery

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pixlee.pixleesdk.PXLAlbumSortOptions
import com.pixlee.pixleesdk.PXLAlbumSortType
import com.sjsoft.app.data.PXLPhotoItem
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


class GalleryViewModel
@Inject constructor(private val pixleeDataSource: PixleeDataSource) : BaseViewModel() {

    val listUI: MutableLiveData<UIData> = MutableLiveData()
    val sortType: MutableLiveData<PXLAlbumSortType> = MutableLiveData()

    sealed class UIData {
        object Loading : UIData()
        class Data(val list: ArrayList<PXLPhotoItem>) : UIData()
        object Error : UIData()
    }

    var loading = false
    fun getLoadData(isLoadingMore: Boolean = false, options: PXLAlbumSortOptions? = null) {
        launchVMScope({
            try {
                if (!isLoadingMore) listUI.value = UIData.Loading
                loading = true
                pixleeDataSource.loadNextPageOfPhotos(options).collect {
                    listUI.value = UIData.Data(it)
                    Log.e("GalleryVM", "GalleryVM.size: ${it.size}")
                }
                loading = false
            } catch (e: Exception) {
                e.printStackTrace()
                if (!isLoadingMore) listUI.value = UIData.Error
                Log.e("GalleryVM", "GalleryVM.error")
            }
        }, {
            loading = false
            if (!isLoadingMore) listUI.value = UIData.Error
        })
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            if (!loading) {
                getLoadData(true)
            }
        }
    }

    fun changeTab(type: PXLAlbumSortType) {
        sortType.value = type
        val options = PXLAlbumSortOptions()
        options.sortType = type
        options.descending = true
        getLoadData(false, options)
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
}