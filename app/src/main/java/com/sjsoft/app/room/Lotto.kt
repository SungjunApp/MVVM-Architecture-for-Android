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
    val drwtNo1: Int,
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int,    //보너스
    val returnValue: LottoDataType,
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

enum class LottoDataType constructor(val value: String) {
    @SerializedName("success")
    success("success"),

    @SerializedName("fail")
    fail("fail");

    companion object {
        fun from(findValue: String): LottoDataType = values().first { it.value == findValue }
    }
}