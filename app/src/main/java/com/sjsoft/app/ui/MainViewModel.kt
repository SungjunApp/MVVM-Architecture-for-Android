package com.sjsoft.app.ui

import com.sjsoft.app.data.repository.PreferenceDataSource
import javax.inject.Inject


class MainViewModel
@Inject constructor(val pref: PreferenceDataSource) : BaseViewModel() {

    fun saveReservedDrwNo(reservedDrwNo:String) {
        pref.setReservedDrwNo(reservedDrwNo)
    }
}

