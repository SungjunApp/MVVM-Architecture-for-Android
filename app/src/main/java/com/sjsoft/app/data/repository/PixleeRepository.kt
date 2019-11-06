package com.sjsoft.app.data.repository

import android.util.Log
import com.pixlee.pixleesdk.PXLAlbum
import com.pixlee.pixleesdk.PXLAlbumSortOptions
import com.pixlee.pixleesdk.PXLClient
import com.pixlee.pixleesdk.PXLPhoto
import com.sjsoft.app.BuildConfig
import com.sjsoft.app.data.PXLPhotoItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.ArrayList

interface PixleeDataSource {
    suspend fun loadNextPageOfPhotos(options: PXLAlbumSortOptions? = null): Flow<ArrayList<PXLPhotoItem>>
    suspend fun uploadImage(path:String)
}

class PixleeRepository constructor(
    private val album: PXLAlbum
) : PixleeDataSource {
    override suspend fun uploadImage(path: String) {




        PXLClient.initialize(BuildConfig.AWS_ACCESS_KEY, BuildConfig.AWS_SECRET_KEY)
        //album.uploadImage("test", "test@test.com", "testuser", "https://timedotcom.files.wordpress.com/2019/05/drake-nba-finals-warning.jpg", false)


    }

    override suspend fun loadNextPageOfPhotos(options: PXLAlbumSortOptions?): Flow<ArrayList<PXLPhotoItem>> =
        flow {
            PXLClient.initialize(BuildConfig.PIXLEE_API_KEY)
            var remoteResult: ArrayList<PXLPhoto>? = null

            options?.also { album.setSortOptions(it) }

            album.loadNextPageOfPhotos(object : PXLAlbum.RequestHandlers {
                override fun DataLoadedHandler(photos: ArrayList<PXLPhoto>) {
                    remoteResult = photos
                    Log.e("GalleryVM", "GalleryVM.remote.size: ${photos.size}")
                }

                override fun DataLoadFailedHandler(error: String?) {
                    //throw Exception(error)
                }
            })

            var isWorking = true
            while (isWorking) {
                delay(2000)
                remoteResult?.also {
                    var lastIndex = -1
                    val result = ArrayList<PXLPhotoItem>()
                    it.forEach {
                        result.add(PXLPhotoItem(++lastIndex, it))
                    }
                    Log.e("GalleryVM", "GalleryVM.remote + old.size: ${result.size}")
                    emit(result)
                    isWorking = false
                }
            }
        }

}