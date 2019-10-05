package com.sjsoft.app.room

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sjsoft.app.util.LottoUtil
import java.math.BigDecimal

@Entity(tableName = "Lotto")
data class Lotto(
    @PrimaryKey
    val drwNo: Int,     //회차
    var drwtNo1: Int,
    var drwtNo2: Int,
    var drwtNo3: Int,
    var drwtNo4: Int,
    var drwtNo5: Int,
    var drwtNo6: Int,
    var bnusNo: Int,    //보너스
    val returnValue: LottoReturnType,
    val drwNoDate: String,
    val totSellamnt: BigDecimal,
    val firstWinamnt: BigDecimal,
    val firstPrzwnerCo: BigDecimal,
    val firstAccumamnt: BigDecimal
) {
    fun getWinNumsAsLit(): List<Int> {
        return listOf(
            drwtNo1,
            drwtNo2,
            drwtNo3,
            drwtNo4,
            drwtNo5,
            drwtNo6,
            bnusNo
        )
    }

    //This is for increasing the rendering performance of HistoryAdapter
    @Ignore
    private var display: String? = null

    fun getDisplayText(): String {
        if (display == null)
            display = LottoUtil.convertIntoString(getWinNumsAsLit())
        return display!!
    }
}

enum class LottoReturnType constructor(val value: String) {
    @SerializedName("success")
    success("success"),

    @SerializedName("fail")
    fail("fail");

    companion object {
        fun from(findValue: String): LottoReturnType = values().first { it.value == findValue }
    }
}