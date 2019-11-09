package com.sjsoft.app.ui.gallery

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pixlee.pixleesdk.PXLAlbumSortOptions
import com.pixlee.pixleesdk.PXLAlbumSortType
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.data.PXLPhotoItem
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class GalleryViewModel
@Inject constructor(private val pixleeDataSource: PixleeDataSource) : BaseViewModel() {

    val listUI = MutableLiveData<UIData>()
    val loadMoreUI = MutableLiveData<Boolean>().apply { value = false }
    val sortType = MutableLiveData<PXLAlbumSortType>()

    sealed class UIData {
        object LoadingShown : UIData()
        object LoadingHide : UIData()
        class Data(val list: ArrayList<PXLPhotoItem>) : UIData()
    }

    var canLoadMore = true
    var job: Job? = null
    fun getLoadData(options: PXLAlbumSortOptions? = null) =
        runBlocking {
            val isFirstPage = options != null

            job?.cancelAndJoin()
            job = launchVMScope({
                try {

                    Log.e("GalleryVM", "GalleryVM.scope.started")
                    if (isFirstPage) listUI.value = UIData.LoadingShown
                    loadMoreUI.value = false
                    canLoadMore = false
                    pixleeDataSource.loadNextPageOfPhotos(options)
                        .collect {
                            listUI.value = UIData.Data(it)
                            Log.e("GalleryVM", "GalleryVM.size: ${it.size}")
                        }
                    canLoadMore = true
                } catch (e: Exception) {
                    canLoadMore = true
                    if (isFirstPage) listUI.value = UIData.LoadingHide
                    e.printStackTrace()
                    loadMoreUI.value = true
                    Log.e("GalleryVM", "GalleryVM.scope.error")
                } finally {
                    Log.e("GalleryVM", "GalleryVM.scope.finished")
                }
            }, {
                Log.e("GalleryVM", "GalleryVM.scope.canceled")
                if (isFirstPage) listUI.value = UIData.LoadingHide
                canLoadMore = true
            })
        }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + AppConfig.LIST_VISIBLE_THRESHOLD >= totalItemCount) {
            if (canLoadMore) {
                getLoadData()
            }
        }
    }

    private val defaultTab = PXLAlbumSortType.RECENCY
    fun changeTab(type: PXLAlbumSortType = defaultTab) {
        sortType.value = type
        val options = PXLAlbumSortOptions()
        options.sortType = type
        options.descending = true
        getLoadData(options)
    }

    fun loadMore() {
        val sortOption: PXLAlbumSortOptions? =
            if (listUI.value is UIData.Data && (listUI.value as UIData.Data).list.isNotEmpty() && sortType.value!=null) {
                null

            } else {
                val options = PXLAlbumSortOptions()
                options.sortType = sortType.value ?: defaultTab
                options.descending = true
                options
            }


        Log.e("GalleryVM", "GalleryVM.loadMore()")
        getLoadData(sortOption)
    }

}