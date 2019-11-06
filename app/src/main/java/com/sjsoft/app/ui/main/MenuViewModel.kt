package com.sjsoft.app.ui.main

import androidx.lifecycle.MutableLiveData
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.global.SingleLiveEvent
import com.sjsoft.app.util.NetworkErrorUtil
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.data.repository.PixleeDataSource
import com.sjsoft.app.room.Lotto
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import javax.inject.Inject


class MenuViewModel
@Inject constructor(private val pixleeDataSource: PixleeDataSource) : BaseViewModel() {
    fun uploadImage(filePath:String){
        launchVMScope({
            pixleeDataSource.uploadImage(filePath)
        },{

        })
    }
}

