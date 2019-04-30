package com.bluewhale.sa.model.source

import com.bluewhale.sa.model.ShiftHalf

class ShiftRepository (val shiftDataSource: ShiftDataSource):
    ShiftDataSource {
    override fun startShift(marker: ShiftHalf, callback: ShiftDataSource.CompletableCallback) {
        shiftDataSource.startShift(marker, callback)
    }

    override fun endShift(marker: ShiftHalf, callback: ShiftDataSource.CompletableCallback) {
        shiftDataSource.endShift(marker, callback)
    }

    override fun getShifts(callback: ShiftDataSource.LoadShiftCallback) {
        shiftDataSource.getShifts(callback)
    }
}