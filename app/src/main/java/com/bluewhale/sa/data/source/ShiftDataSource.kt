package com.bluewhale.sa.data.source

import com.bluewhale.sa.data.Shift
import com.bluewhale.sa.data.ShiftHalf

interface ShiftDataSource {
    interface LoadShiftCallback {
        fun onShiftsLoaded(shifts: List<Shift>)
        fun onDataNotAvailable()
    }

    interface CompletableCallback {
        fun onComplete(shifts: List<Shift>)
        fun onError(message:String?)
    }

    fun startShift(marker: ShiftHalf, callback:CompletableCallback)
    fun endShift(marker: ShiftHalf, callback:CompletableCallback)
    fun getShifts(callback: LoadShiftCallback)

}