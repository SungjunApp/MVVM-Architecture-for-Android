package com.sjsoft.app.util

import java.lang.StringBuilder
import kotlin.random.Random

data class LottoTicket(
    val list: List<Int>,
    val displayText: String
)

class LottoUtil {
    companion object {
        /**
         * @return
         * size : 7
         * - 6 win numbers + 1 bonus number
         */
        fun generateWinNumbers(): LottoTicket {
            val result = ArrayList<Int>()
            val list = ArrayList<Int>()
            for (i in 1..45) {
                list.add(i)
            }

            for (i in 0 until 6) {
                val idx = Random.nextInt(list.size)
                result.add(list[idx])
                list.remove(idx)
            }
            result.sortBy { it }

            val idx = Random.nextInt(list.size)
            result.add(list[idx])
            list.remove(idx)
            //val result = arrayListOf(	2	,9	,16,	25,	26,	40,	42)

            return LottoTicket(
                result,
                convertIntoString(result)
            )
        }

        /**
         * @param lotto numbers
         * 0~5 :number
         * 6 : bonus
         *
         * @return formatted string
         *         Example : 1, 30, 29, 3, 4, 6, 8
         *         - Number : 1, 30, 29, 3, 4, 6
         *         - Bonus : 8
         */
        fun convertIntoString(result: List<Int>): String {
            val sb = StringBuilder()
            val size = result.size
            for ((i, num) in result.withIndex()) {
                if (sb.isNotEmpty()) {
                    if (i == size - 1)
                        sb.append(" + ")
                    else
                        sb.append(", ")
                }
                sb.append(num)

            }
            return sb.toString()
        }
    }
}