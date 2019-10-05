package com.android.app.util

import com.sjsoft.app.util.LottoUtil
import org.junit.*

class LottoUtilTest {
    @Test
    fun `lotto generation`() {
        for(i in 0..100){
            val numbers = LottoUtil.generateWinNumbers()
            Assert.assertEquals(7, numbers.list.size)

            val list = numbers.list
            val displayText = "${list[0]}, ${list[1]}, ${list[2]}, ${list[3]}, ${list[4]}, ${list[5]} + ${list[6]}"
            Assert.assertEquals(displayText, numbers.displayText)
        }
    }

    @Test
    fun `lotto display text test`() {
        val list = ArrayList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        list.add(6)
        list.add(7) // bonus
        val display = LottoUtil.convertIntoString(list)
        Assert.assertEquals("1, 2, 3, 4, 5, 6 + 7", display)
    }
}