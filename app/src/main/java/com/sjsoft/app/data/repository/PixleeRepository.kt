package com.sjsoft.app.data.repository

import android.util.Log
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.pixlee.pixleesdk.PXLAlbum
import com.pixlee.pixleesdk.PXLAlbumSortOptions
import com.pixlee.pixleesdk.PXLClient
import com.pixlee.pixleesdk.PXLPhoto
import com.sjsoft.app.BuildConfig
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.data.PXLPhotoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList

interface PixleeDataSource {
    suspend fun loadNextPageOfPhotos(options: PXLAlbumSortOptions? = null): Flow<ArrayList<PXLPhotoItem>>
    suspend fun uploadImage(title: String, filePath: String, contentType: String): String
    suspend fun getS3Images(): List<S3ObjectSummary>
}

class PixleeRepository constructor(
    private val album: PXLAlbum,
    private val awsS3: AmazonS3
) : PixleeDataSource {
    override suspend fun getS3Images(): List<S3ObjectSummary> {
        var list: List<S3ObjectSummary> = ArrayList()
        withContext(Dispatchers.IO) {
            list = awsS3.listObjects(BuildConfig.AWS_S3_BUCKET_NAME).objectSummaries

            Log.e("GalleryVM", "GalleryVM.summaries.size: ${list.size}")

            list.forEach {
                Log.e("GalleryVM", "GalleryVM.awsS3.key: ${it.key}")
            }
        }
        return list
    }

    override suspend fun uploadImage(title: String, filePath: String, contentType: String): String {
        var url = ""
        val keyName = "${AppConfig.pixleeEmail}/${UUID.randomUUID()}"
        withContext(Dispatchers.IO) {
            //awsS3.deleteObject(BuildConfig.AWS_S3_BUCKET_NAME, keyName)

            val request = PutObjectRequest(BuildConfig.AWS_S3_BUCKET_NAME, keyName, File(filePath))
            val metadata = ObjectMetadata()
            //metadata.contentType = "image"
            metadata.contentType = contentType
            metadata.addUserMetadata("x-amz-meta-title", title)
            request.metadata = metadata
            awsS3.putObject(request)
            awsS3.setObjectAcl(BuildConfig.AWS_S3_BUCKET_NAME, keyName, CannedAccessControlList.PublicRead)

            url = awsS3.getUrl(BuildConfig.AWS_S3_BUCKET_NAME, keyName).toExternalForm()

            PXLClient.initialize(BuildConfig.PIXLEE_API_KEY, BuildConfig.PIXLEE_SECRET_KEY)

            album.uploadImage(title, AppConfig.pixleeEmail, AppConfig.pixleeUserName, url, true)
        }
        return url
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

                if (type == jobError) {
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