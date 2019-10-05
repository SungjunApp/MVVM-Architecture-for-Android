package com.android.app.room

import com.android.app.util.LottoTestUtil
import com.sjsoft.app.util.LottoUtil
import org.junit.*

class LottoTest {
    @Test
    fun `lotto list`() {
        val lotto = LottoTestUtil.makeLotto(37)
        val listFromLotto = lotto.getWinNumsAsLit()
        val listFromManual = listOf(
            lotto.drwtNo1,
            lotto.drwtNo2,
            lotto.drwtNo3,
            lotto.drwtNo4,
            lotto.drwtNo5,
            lotto.drwtNo6,
            lotto.bnusNo
        )
        Assert.assertEquals(listFromLotto, listFromManual)

    }

}