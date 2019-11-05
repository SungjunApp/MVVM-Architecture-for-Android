package com.sjsoft.app.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


class SplashViewModel
@Inject constructor(val pixleeDataSource: PixleeDataSource) : BaseViewModel() {
    val uiData: MutableLiveData<UIData> = MutableLiveData()

    sealed class UIData {
        object Loading : UIData()
        object Welcome : UIData()
        object Main : UIData()
        object Error : UIData()
    }

    fun syncData() {
        launchVMScope({
            val delay = async {
                delay(AppConfig.splashDelay)
            }

            val result = async {
                pixleeDataSource.loadNextPageOfPhotos().collect {
                    it.forEach {
                        Log.e("SplashView", "it.photoTitle: ${it.photoTitle}")
                    }
                }
            }

            delay.await()
            uiData.value = UIData.Loading

            result.await()
            uiData.value = UIData.Main
        }, {
            uiData.value = UIData.Error
        })
    }


}