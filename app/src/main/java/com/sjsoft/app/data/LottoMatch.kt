package com.sjsoft.app.data

import android.content.Context
import com.sjsoft.app.R
import com.sjsoft.app.room.Lotto
import com.sjsoft.app.util.LottoTicket

data class LottoMatch(
    val lottoOfficial: Lotto,
    val lottoTicket: LottoTicket
) {
    enum class WinType(val ranking: Int) {
        rank1(1),
        rank2(2),
        rank3(3),
        rank4(4),
        rank5(5),
        failur(0)
    }

    fun getWinType(): WinType {
        var wincount = 0
        var bonusCount = 0
        if (lottoOfficial.drwtNo1 == lottoTicket.list[0]) {
            wincount++
        }

        if (lottoOfficial.drwtNo2 == lottoTicket.list[1]) {
            wincount++
        }

        if (lottoOfficial.drwtNo3 == lottoTicket.list[2]) {
            wincount++
        }

        if (lottoOfficial.drwtNo4 == lottoTicket.list[3]) {
            wincount++
        }

        if (lottoOfficial.drwtNo5 == lottoTicket.list[4]) {
            wincount++
        }

        if (lottoOfficial.drwtNo6 == lottoTicket.list[5]) {
            wincount++
        }

        if (lottoOfficial.bnusNo == lottoTicket.list[6]) {
            bonusCount++
        }

        return if (wincount == 6) {
            WinType.rank1
        } else if (wincount == 5 && bonusCount == 1) {
            WinType.rank2
        } else if (wincount == 5) {
            WinType.rank3
        } else if (wincount == 4) {
            WinType.rank4
        } else if (wincount == 3) {
            WinType.rank5
        } else {
            WinType.failur
        }
    }

    class PopupMessage(val title: String, val message: String)

    fun getPopupMessage(context: Context): PopupMessage {
        val winType = getWinType()
        val title = when (winType) {
            WinType.failur -> context.getString(R.string.title_lose)
            else -> context.getString(R.string.title_win)
        }

        val message = when (winType) {
            WinType.failur -> {
                context.getString(
                    R.string.message_lost,
                    lottoTicket.displayText,
                    lottoOfficial.getDisplayText()
                )
            }
            else -> context.getString(R.string.message_win, winType.ranking)
        }
        return PopupMessage(title, message)
    }
}
