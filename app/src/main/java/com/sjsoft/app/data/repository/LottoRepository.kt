package com.sjsoft.app.data.repository

import com.sjsoft.app.data.api.RemoteAPI
import com.sjsoft.app.room.Frequency
import com.sjsoft.app.room.Lotto
import com.sjsoft.app.room.LottoDao
import com.sjsoft.app.room.LottoReturnType

interface LottoDataSource {
    suspend fun loadAllLottos():List<Lotto>
    suspend fun syncWinnersInfo(startDrwNo: Int, endDrwNo: Int)
    suspend fun getWinder(drwNo: Int): Lotto
    suspend fun loadAllFrequency():List<Frequency>
}

class LottoRepository constructor(
    private val lottoAPI: RemoteAPI,
    private val lottoDao: LottoDao
) : LottoDataSource {
    override suspend fun loadAllFrequency(): List<Frequency> {
        return lottoDao.loadAllFrequencies()
    }

    override suspend fun loadAllLottos(): List<Lotto> {
        return lottoDao.loadAllLottos()
    }

    override suspend fun getWinder(drwNo: Int): Lotto {
        val localLotto = lottoDao.findLotto(drwNo)
        if (localLotto != null) {
            return localLotto
        } else {
            val serverLotto = lottoAPI.getLotto(drwNo)
            when (serverLotto.returnValue) {
                LottoReturnType.success -> return serverLotto
                LottoReturnType.fail -> throw IllegalArgumentException()
            }
        }
    }

    /**
     * Caching Winners info
     *
     * 1. find a saved Lotto
     *      2-A. if null, call api and save it
     *      2-B. if not null, skip it
     * 2. Iterates
     */
    override suspend fun syncWinnersInfo(startDrwNo: Int, endDrwNo: Int) {
        for (drwNo in startDrwNo..endDrwNo) {
            val localLotto = lottoDao.findLotto(drwNo)
            if (localLotto != null) {
                return
            } else {
                val serverLotto = lottoAPI.getLotto(drwNo)
                when (serverLotto.returnValue) {
                    LottoReturnType.success -> lottoDao.saveLottoWithFrequency(serverLotto)
                    LottoReturnType.fail -> throw IllegalArgumentException()
                }
            }
        }
    }
}