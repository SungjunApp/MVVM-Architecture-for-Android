package com.sjsoft.app.di

import android.content.Context
import com.sjsoft.app.data.api.LottoAPI
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.data.repository.PreferenceRepository
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.data.repository.LottoRepository
import com.sjsoft.app.room.LottoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(api: LottoAPI, dao: LottoDao): LottoDataSource {
        return LottoRepository(api, dao)
    }

    @Singleton
    @Provides
    fun providePreferenceRepository(context: Context): PreferenceDataSource {
        return PreferenceRepository(context)
    }


}