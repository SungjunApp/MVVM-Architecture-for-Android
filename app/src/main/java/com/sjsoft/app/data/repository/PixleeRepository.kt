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
import java.lang.IllegalArgumentException
import java.util.ArrayList

interface PixleeDataSource {
    suspend fun loadNextPageOfPhotos(options: PXLAlbumSortOptions? = null): Flow<ArrayList<PXLPhotoItem>>
    suspend fun uploadImage(path: String)
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
            val jobFinished = -1
            val jobWorking = 0
            val jobError = 1
            var type = jobWorking

            PXLClient.initialize(BuildConfig.PIXLEE_API_KEY)
            var remoteResult: ArrayList<PXLPhoto>? = null

            options?.also { album.setSortOptions(it) }

            album.loadNextPageOfPhotos(object : PXLAlbum.RequestHandlers {
                override fun DataLoadedHandler(photos: ArrayList<PXLPhoto>) {
                    remoteResult = photos
                    Log.e("GalleryVM", "GalleryVM.remote.size: ${photos.size}")
                }

                override fun DataLoadFailedHandler(error: String?) {
                    type = jobError
                    //error(error ?: "data load failed")

                }
            })

            while (type >= jobWorking) {
                delay(700)

                if(type==jobError){
                    throw IllegalArgumentException()
                }

                remoteResult?.also {
                    var lastIndex = -1
                    val result = ArrayList<PXLPhotoItem>()

                    Log.e("GalleryVM", "GalleryVM.remote + remoteResult-pre.size: ${it.size}")
                    it.forEach {
                        result.add(PXLPhotoItem(++lastIndex, it))
                    }
                    Log.e("GalleryVM", "GalleryVM.remote + old.size: ${result.size}")
                    Log.e("GalleryVM", "GalleryVM.remote + remoteResult-post.size: ${it.size}")
                    emit(result)
                    type = jobFinished
                }
            }
        }

}