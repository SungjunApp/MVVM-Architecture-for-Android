package com.android.app.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.android.app.util.LottoTestUtil
import com.sjsoft.app.data.api.LottoAPI
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.data.repository.LottoRepository
import com.sjsoft.app.room.AppDatabase
import com.sjsoft.app.room.Lotto
import com.sjsoft.app.room.LottoDao
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import java.io.IOException
import org.mockito.Mockito.mock
import java.util.*
import kotlin.collections.HashMap

@RunWith(AndroidJUnit4::class)
@LargeTest
class LottoRepositoryTest {
//    @Rule
//    @JvmField
//    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var lottoDao: LottoDao
    private lateinit var db: AppDatabase

    //var lottoAPI: LottoAPI = mock()

    private val lottoAPI = mock(LottoAPI::class.java)

    lateinit var repo: LottoDataSource

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        lottoDao = db.getLottoDao()
        repo = LottoRepository(lottoAPI, lottoDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun syncWinnerInfo() = runBlocking {

        val frequencyMap = HashMap<Int, Int>() //Frequency - first: winNo, second: count
        val savedLottos = HashMap<Int, Lotto>()
        val startNo = 1
        val endNo = 50
        for (i in startNo..endNo) {
            val lotto = LottoTestUtil.makeLotto(i)
            savedLottos[lotto.drwNo] = lotto

            val list = lotto.getWinNumsAsLit()
            for (num in list) {
                val frequency = frequencyMap[num]
                frequencyMap[num] = if (frequency == null)
                    1
                else
                    frequency + 1
            }

            `when`(lottoAPI.getLotto(i)).thenReturn(lotto)
        }

        repo.syncWinnersInfo(startNo, endNo)

        val lottos = lottoDao.loadAllLottos()

        for (one in lottos) {
            assertEquals(
                one,
                savedLottos[one.drwNo]
            )
        }

        val frequencies = lottoDao.loadAllFrequencies()
        for (one in frequencies) {
            assertEquals(
                one.count,
                frequencyMap[one.winNo]
            )
        }
    }

    @Test
    @Throws(Exception::class)
    fun getWinder_nothingLocal() = runBlocking {
        val drwNo = Random().nextInt(50) + 1
        val lotto = LottoTestUtil.makeLotto(drwNo)

        `when`(lottoAPI.getLotto(drwNo)).thenReturn(lotto)
        val oneFromRepo = repo.getWinder(drwNo)

        assertEquals(
            oneFromRepo,
            lotto
        )
    }

    @Test
    @Throws(Exception::class)
    fun getWinder_hasLocal() = runBlocking {
        val drwNo = Random().nextInt(50) + 1
        val lotto = LottoTestUtil.makeLotto(drwNo)

        lottoDao.saveLottoWithFrequency(lotto)
        val oneFromRepo = repo.getWinder(drwNo)
        assertEquals(
            oneFromRepo,
            lotto
        )
    }

}