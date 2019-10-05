package com.sjsoft.app.ui.welcome

import com.sjsoft.app.global.SingleLiveEvent
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.ui.BaseViewModel
import javax.inject.Inject


class WelcomeViewModel
@Inject constructor(val pref: PreferenceDataSource) : BaseViewModel() {
    val command: SingleLiveEvent<Int> = SingleLiveEvent()

    fun start() {
        pref.setReadWelcome(true)
        command.value = 1
    }
}