package com.android.app.util

import com.sjsoft.app.room.Lotto
import com.sjsoft.app.room.LottoReturnType
import com.sjsoft.app.util.LottoUtil

class LottoTestUtil {
    companion object {
        fun makeLotto(drwNo: Int, type: LottoReturnType = LottoReturnType.success): Lotto {
            val numbers = LottoUtil.generateWinNumbers().list
            return Lotto(
                drwNo = drwNo,
                drwtNo1 = numbers[0],
                drwtNo2 = numbers[1],
                drwtNo3 = numbers[2],
                drwtNo4 = numbers[3],
                drwtNo5 = numbers[4],
                drwtNo6 = numbers[5],
                bnusNo = numbers[6],    //보너스
                returnValue = type,
                drwNoDate = "2019-0101",
                totSellamnt = 100.toBigDecimal(),
                firstWinamnt = 100.toBigDecimal(),
                firstPrzwnerCo = 100.toBigDecimal(),
                firstAccumamnt = 100.toBigDecimal()
            )
        }
    }
}