package com.android.app.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sjsoft.app.R
import com.sjsoft.app.data.LottoMatch
import com.sjsoft.app.room.Lotto
import com.sjsoft.app.room.LottoReturnType
import com.sjsoft.app.util.LottoTicket
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LottoMatchTest {
    lateinit var context: Context
    @Before
    fun createDb() {
        context = ApplicationProvider.getApplicationContext<Context>()
    }

    val lotto = Lotto(
        drwNo = 300,
        drwtNo1 = 1,
        drwtNo2 = 2,
        drwtNo3 = 3,
        drwtNo4 = 4,
        drwtNo5 = 5,
        drwtNo6 = 6,
        bnusNo = 7,    //보너스
        returnValue = LottoReturnType.success,
        drwNoDate = "2019-0101",
        totSellamnt = 100.toBigDecimal(),
        firstWinamnt = 100.toBigDecimal(),
        firstPrzwnerCo = 100.toBigDecimal(),
        firstAccumamnt = 100.toBigDecimal()
    )

    @Test
    @Throws(Exception::class)
    fun rank1() {
        val lotto = lotto.copy()
        val ticket = LottoTicket(lotto.getWinNumsAsLit(), lotto.getDisplayText())
        win(
            LottoMatch(lotto, ticket),
            LottoMatch.WinType.rank1
        )
    }

    @Test
    @Throws(Exception::class)
    fun rank2() {
        val ticket = LottoTicket(lotto.getWinNumsAsLit(), lotto.getDisplayText())
        val lotto = lotto.copy()
        lotto.drwtNo1 = 10
        win(
            LottoMatch(lotto, ticket),
            LottoMatch.WinType.rank2
        )
    }

    @Test
    @Throws(Exception::class)
    fun rank3() {
        val ticket = LottoTicket(lotto.getWinNumsAsLit(), lotto.getDisplayText())
        val lotto = lotto.copy()
        lotto.drwtNo1 = 10
        lotto.bnusNo = 20
        win(
            LottoMatch(lotto, ticket),
            LottoMatch.WinType.rank3
        )
    }

    @Test
    @Throws(Exception::class)
    fun rank4() {
        val ticket = LottoTicket(lotto.getWinNumsAsLit(), lotto.getDisplayText())
        val lotto = lotto.copy()
        lotto.drwtNo1 = 10
        lotto.drwtNo2 = 11
        win(
            LottoMatch(lotto, ticket),
            LottoMatch.WinType.rank4
        )
    }

    @Test
    @Throws(Exception::class)
    fun rank5() {
        val ticket = LottoTicket(lotto.getWinNumsAsLit(), lotto.getDisplayText())
        val lotto = lotto.copy()
        lotto.drwtNo1 = 10
        lotto.drwtNo2 = 11
        lotto.drwtNo3 = 12
        win(
            LottoMatch(lotto, ticket),
            LottoMatch.WinType.rank5
        )
    }

    @Test
    @Throws(Exception::class)
    fun fail() {
        val ticket = LottoTicket(lotto.getWinNumsAsLit(), lotto.getDisplayText())
        val lotto = lotto.copy()
        lotto.drwtNo1 = 10
        lotto.drwtNo2 = 11
        lotto.drwtNo3 = 12
        lotto.drwtNo4 = 13
        lose(LottoMatch(lotto, ticket), LottoMatch.WinType.failur)

        lotto.drwtNo5 = 14
        lose(LottoMatch(lotto, ticket), LottoMatch.WinType.failur)

        lotto.drwtNo6 = 15
        lose(LottoMatch(lotto, ticket), LottoMatch.WinType.failur)

        lotto.bnusNo = 20
        lose(LottoMatch(lotto, ticket), LottoMatch.WinType.failur)
    }

    private fun win(oriMatch: LottoMatch, oriWintype: LottoMatch.WinType) {
        oriMatch.getWinType().also { winType ->
            assertEquals(oriWintype, winType)
            oriMatch.getPopupMessage(context).apply {
                assertEquals(context.getString(R.string.title_win), title)
                assertEquals(context.getString(R.string.message_win, winType.ranking), message)
            }
        }
    }

    private fun lose(oriMatch: LottoMatch, oriWintype: LottoMatch.WinType) {
        oriMatch.getWinType().also { winType ->
            assertEquals(oriWintype, winType)
            oriMatch.getPopupMessage(context).apply {
                assertEquals(context.getString(R.string.title_lose), title)
                assertEquals(
                    context.getString(
                        R.string.message_lost,
                        oriMatch.lottoTicket.displayText,
                        oriMatch.lottoOfficial.getDisplayText()
                    ),
                    message
                )
            }
        }
    }
}