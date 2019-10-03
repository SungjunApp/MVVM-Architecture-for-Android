package com.sjsoft.app.di

import android.content.Context
import com.sjsoft.app.network.api.APIUser
import com.sjsoft.app.network.repository.PreferenceDataSource
import com.sjsoft.app.network.repository.PreferenceRepository
import com.sjsoft.app.network.repository.UserDataSource
import com.sjsoft.app.network.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(api: APIUser): UserDataSource {
        return UserRepository(api)
    }

    @Singleton
    @Provides
    fun providePreferenceRepository(context: Context): PreferenceDataSource {
        return PreferenceRepository(context)
    }


}