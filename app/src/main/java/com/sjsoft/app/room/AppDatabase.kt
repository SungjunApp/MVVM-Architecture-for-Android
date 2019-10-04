package com.sjsoft.app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bluewhale.ssb.room.GameStat

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        GameStat::class
    ]
)
//@TypeConverters(StudyPlanConverters::class, DDayConverters::class, ColorConverters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase.db")
                //.addMigrations(migration_1_2, migration_2_3)
                .build()
        }

    }

    //abstract fun getGameStatDao(): GameStatDao
}