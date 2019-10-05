package com.sjsoft.app.ui.splash

import androidx.lifecycle.MutableLiveData
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import javax.inject.Inject


class SplashViewModel
@Inject constructor(val api: LottoDataSource, val pref: PreferenceDataSource) : BaseViewModel() {
    val uiData: MutableLiveData<UIData> = MutableLiveData()

    sealed class UIData {
        object Loading : UIData()
        object Welcome : UIData()
        object Main : UIData()
        object Error : UIData()
    }

    fun syncData() {
        launchVMScope({
            try {
                val delay = async { delay(AppConfig.splashDelay) }
                val result = async { api.syncWinnersInfo(1, 50) }

                delay.await()
                uiData.value = UIData.Loading

                result.await()
                val readWelcome = pref.getReadWelcome()
                uiData.value = if(readWelcome)
                    UIData.Main
                else
                    UIData.Welcome
            } finally {

            }
        }, {
            uiData.value = UIData.Error
        })
    }


}