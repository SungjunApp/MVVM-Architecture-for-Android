package com.sjsoft.app.ui.upload

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


class UploadViewModel
@Inject constructor(private val pixleeDataSource: PixleeDataSource) : BaseViewModel() {

}

