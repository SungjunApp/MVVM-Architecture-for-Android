package com.bluewhale.sa.data.source

import com.bluewhale.sa.data.ShiftHalf

class ShiftRepository (val shiftDataSource:ShiftDataSource): ShiftDataSource {
    override fun startShift(marker: ShiftHalf, callback: ShiftDataSource.CompletableCallback) {
        shiftDataSource.startShift(marker, callback)
    }

    override fun endShift(marker: ShiftHalf, callback: ShiftDataSource.CompletableCallback) {
        shiftDataSource.endShift(marker, callback)
    }

    override fun getShifts(callback: ShiftDataSource.LoadShiftCallback) {
        shiftDataSource.getShifts(callback)
    }


    companion object {

        private var INSTANCE: ShiftRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param shiftDataSource the backend data source
         * *
         * @return the [ShiftRepository] instance
         */
        @JvmStatic fun getInstance(shiftDataSource: ShiftDataSource) =
            INSTANCE ?: synchronized(ShiftRepository::class.java) {
                INSTANCE ?: ShiftRepository(shiftDataSource)
                    .also { INSTANCE = it }
            }


        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}