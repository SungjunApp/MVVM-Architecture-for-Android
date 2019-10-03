package com.sjsoft.app.ui.register

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sjsoft.app.global.SingleLiveEvent
import com.sjsoft.app.network.NetworkErrorHandler
import com.sjsoft.app.network.api.LoginInfo
import com.sjsoft.app.network.api.LoginResult
import com.sjsoft.app.network.repository.UserDataSource
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import javax.inject.Inject


class LoginViewModel
@Inject constructor(val api: UserDataSource) : BaseViewModel() {
    /*protected val nextButton = MutableLiveData<Boolean>().apply { value = false }
    val nextButton: LiveData<Boolean>
        get() = _nextButton*/
    val nextButton = MutableLiveData<Boolean>().apply { value = false }

    val command: SingleLiveEvent<Command> = SingleLiveEvent()
    val data: MutableLiveData<ListData> = MutableLiveData()

    sealed class ListData {
        data class Data(val data: LoginResult) : ListData()
        object Error : ListData()
    }

    sealed class Command {
        data class Loading(val showing: Boolean) : Command()
        data class Error(@StringRes val errorRes: Int) : Command()
    }

    var id: String = ""
        set(value) {
            field = value
            nextButton.value = value.isNotEmpty() && pw.isNotEmpty()
        }
    var pw: String = ""
        set(value) {
            field = value
            nextButton.value = value.isNotEmpty() && id.isNotEmpty()
        }
    fun login() {
        if (data.value != null && data.value is ListData.Data)
            return

        launchVMScope({
            try {
                val delay = async { delay(400) }
                val result = async { api.login(id, pw) }

                delay.await()
                command.value = Command.Loading(true)

                data.value = ListData.Data(result.await())

            } finally {
                command.value = Command.Loading(false)
            }
        }, {
            NetworkErrorHandler.handleError(it)?.also { stringRes ->
                data.value = ListData.Error
                command.value = Command.Error(stringRes)
            }
        })
    }


}