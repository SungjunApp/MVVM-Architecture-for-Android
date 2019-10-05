package com.sjsoft.app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Lotto::class,
        Frequency::class
    ]
)
@TypeConverters(LottoConverters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase.db")
                //.addMigrations(migration_1_2, migration_2_3)
                .build()
        }

    }

    abstract fun getLottoDao(): LottoDao
}