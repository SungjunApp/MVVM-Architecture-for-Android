package com.sjsoft.app.room

import androidx.room.TypeConverter
import java.math.BigDecimal

class LottoConverters{
    @TypeConverter
    fun convertLottoDataTypeToString(value: LottoReturnType): String {
        return value.value
    }

    @TypeConverter
    fun convertStringToLottoDataType(value: String): LottoReturnType {
        return LottoReturnType.from(value)
    }

    @TypeConverter
    fun convertBigDecimalToString(value: BigDecimal): String {
        return value.toString()
    }

    @TypeConverter
    fun convertStringToBigDecimal(value: String): BigDecimal {
        return BigDecimal(value)
    }

}