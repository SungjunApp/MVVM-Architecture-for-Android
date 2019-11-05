package com.sjsoft.app.data.repository

import com.pixlee.pixleesdk.PXLAlbum
import com.pixlee.pixleesdk.PXLPhoto
import com.sjsoft.app.data.api.LottoAPI
import com.sjsoft.app.di.PixleeModule
import com.sjsoft.app.room.Frequency
import com.sjsoft.app.room.Lotto
import com.sjsoft.app.room.LottoDao
import com.sjsoft.app.room.LottoReturnType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.ArrayList

interface PixleeDataSource {
    suspend fun loadNextPageOfPhotos(): Flow<ArrayList<PXLPhoto>>
}

class PixleeRepository constructor(
    private val album: PXLAlbum
) : PixleeDataSource {

    override suspend fun loadNextPageOfPhotos(): Flow<ArrayList<PXLPhoto>> = flow {
        var result : ArrayList<PXLPhoto>? = null
        album.loadNextPageOfPhotos(object : PXLAlbum.RequestHandlers {
            override fun DataLoadedHandler(photos: ArrayList<PXLPhoto>) {
                result = photos
            }

            override fun DataLoadFailedHandler(error: String?) {
                throw Exception(error)
            }
        })

        var canceled = false
        while (canceled) {
            delay(2000)
            result?.also{
                emit(it)
                canceled = true
            }
        }

    }

}