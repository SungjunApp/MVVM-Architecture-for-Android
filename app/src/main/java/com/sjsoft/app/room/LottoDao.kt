package com.sjsoft.app.room

import androidx.room.*
import io.reactivex.Completable

@Dao
interface LottoDao {
    //##########
    //Lotto
    //##########
    @Delete
    fun delete(bookmark: Lotto): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(lotto: Lotto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(list: List<Lotto>)

    @Query("SELECT * FROM Lotto ORDER BY drwNo DESC")
    suspend fun loadAllLottos(): List<Lotto>

    @Query("SELECT * FROM Lotto WHERE drwNo=:drwNo")
    suspend fun findLotto(drwNo: Int): Lotto?

    //##########
    //Frequency
    //##########
    @Query("SELECT * FROM Frequency WHERE winNo=:winNo")
    suspend fun findFrequency(winNo: Int): Frequency?

    @Query("SELECT * FROM Frequency ORDER BY count DESC")
    suspend fun loadAllFrequencies(): List<Frequency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(frequency: Frequency)

    //##########
    //Transaction
    //##########
    @Transaction
    suspend fun saveLottoWithFrequency(serverLotto: Lotto) {
        val localLotto = findLotto(serverLotto.drwNo)
        if (localLotto != null) {
            //Already Saved
            return
        }

        //Save Lotto
        put(serverLotto)

        //Increase Frequency
        val list = serverLotto.getWinNumsAsLit()

        for (num in list) {
            var frequency = findFrequency(num)
            if (frequency == null)
                frequency = Frequency(num, 1)
            else
                frequency.count++

            put(frequency)
        }
    }
}