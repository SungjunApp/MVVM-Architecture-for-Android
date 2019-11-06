package com.sjsoft.app.di

import android.content.Context
import com.pixlee.pixleesdk.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.sjsoft.app.BuildConfig
import com.sjsoft.app.constant.AppConfig

@Module
class PixleeModule{
    @Provides
    //@Singleton
    fun getPXLAlbum(context:Context):PXLAlbum{
        PXLClient.initialize(BuildConfig.AWS_ACCESS_KEY, BuildConfig.AWS_SECRET_KEY)

        val fo = PXLAlbumFilterOptions()
        fo.minTwitterFollowers = 0
        fo.minInstagramFollowers = 0

        val so = PXLAlbumSortOptions()
        so.sortType = PXLAlbumSortType.RECENCY
        so.descending = true

        val album = PXLAlbum(AppConfig.albumId, context)
        album.setPerPage(20)
        album.setFilterOptions(fo)
        album.setSortOptions(so)

        return album
    }
}