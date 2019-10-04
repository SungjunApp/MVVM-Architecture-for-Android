package com.sjsoft.app.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface GameStatDao{
    @Delete
    fun delete(bookmark: GameStat): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(list: GameStat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(list: List<GameStat>)

    @Query("SELECT * FROM GameStat")
    fun loadAll(): Maybe<List<GameStat>>

    @Query("SELECT * FROM GameStat WHERE pkgId=:pkgId")
    fun findOne(pkgId:String): GameStat?
}