package com.sjsoft.app.ui.gallery

import android.util.Log
import androidx.annotation.VisibleForTesting
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

    val listUI = MutableLiveData<ListUI>()
    val loadMoreUI = MutableLiveData<Boolean>().apply { value = false }
    val sortType = MutableLiveData<PXLAlbumSortType>()

    sealed class ListUI {
        object LoadingShown : ListUI()
        object LoadingHide : ListUI()
        class Data(val list: ArrayList<PXLPhotoItem>) : ListUI()
    }

    var canLoadMore = true
    var job: Job? = null
    private fun loadList(options: PXLAlbumSortOptions? = null) = runBlocking {
        val isFirstPage = options != null
        job?.cancelAndJoin()
        job = launchVMScope({
            try {
                if (isFirstPage) listUI.value = ListUI.LoadingShown
                loadMoreUI.value = false
                canLoadMore = false
                pixleeDataSource.loadNextPageOfPhotos(options)
                    .collect {
                        listUI.value = ListUI.Data(it)
                    }
                canLoadMore = true
            } catch (e: Exception) {
                e.printStackTrace()
                if (isFirstPage) listUI.value = ListUI.LoadingHide
                loadMoreUI.value = true
                canLoadMore = true
            }
        }, {
            if (isFirstPage) listUI.value = ListUI.LoadingHide
            loadMoreUI.value = true
            canLoadMore = true
        })
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        val canScroll = visibleItemCount + lastVisibleItemPosition + AppConfig.LIST_VISIBLE_THRESHOLD >= totalItemCount
        Log.e("GalleryVM", "canScroll: $canScroll, \tvisibleCount: $visibleItemCount, lastItemPosition: $lastVisibleItemPosition, leftTotal: ${visibleItemCount + lastVisibleItemPosition + AppConfig.LIST_VISIBLE_THRESHOLD},  globalTotal: $totalItemCount")
        if (visibleItemCount + lastVisibleItemPosition + AppConfig.LIST_VISIBLE_THRESHOLD >= totalItemCount) {
            if (canLoadMore) {
                loadList()
            }
        }
    }

    private val defaultTab = PXLAlbumSortType.RECENCY
    fun changeTab(option: PXLAlbumSortOptions = generateSortOption(defaultTab)) {
        sortType.value = option.sortType
        loadList(option)
    }

    fun generateSortOption(type: PXLAlbumSortType): PXLAlbumSortOptions {
        val options = PXLAlbumSortOptions()
        options.sortType = type
        options.descending = true
        return options
    }

    fun loadMore() {
        val sortOption: PXLAlbumSortOptions? =
            if (listUI.value is ListUI.Data && (listUI.value as ListUI.Data).list.isNotEmpty() && sortType.value != null) {
                null

            } else {
                val options = PXLAlbumSortOptions()
                options.sortType = sortType.value ?: defaultTab
                options.descending = true
                options
            }


        Log.e("GalleryVM", "GalleryVM.loadMore()")
        loadList(sortOption)
    }

}