package com.sjsoft.app.di

import android.content.Context
import com.sjsoft.app.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): AppDatabase {
        return AppDatabase.create(context)
    }
}