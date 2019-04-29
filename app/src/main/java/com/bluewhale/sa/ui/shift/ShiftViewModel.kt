package com.bluewhale.sa.ui.shift

import android.location.Location
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bluewhale.sa.R
import com.bluewhale.sa.data.Shift
import com.bluewhale.sa.data.ShiftHalf
import com.bluewhale.sa.data.source.ShiftDataSource
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.network.api.ShiftAPI
import com.bluewhale.sa.ui.BaseViewModel
import com.bluewhale.sa.ui.shift.work.UserStatus
import java.text.SimpleDateFormat
import java.util.*


class ShiftViewModel(
    private val shiftRepository: ShiftAPI
) : BaseViewModel() {

    //private val _items = MutableLiveData<List<Shift>>().apply { value = emptyList() }
    private val _items = MutableLiveData<List<Shift>>().apply { value = emptyList() }
    val items: LiveData<List<Shift>>
        get() = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _shiftButton = MutableLiveData<UserStatus>()
    val shiftButton: LiveData<UserStatus>
        get() = _shiftButton

    private val _snackbarInt = MutableLiveData<Event<Int>>()
    val snackbarInt: LiveData<Event<Int>>
        get() = _snackbarInt

    private val _snackbarString = MutableLiveData<Event<String>>()
    val snackbarString: LiveData<Event<String>>
        get() = _snackbarString

    private val _shiftAvailable = MutableLiveData<Boolean>()
    val shiftAvailable: LiveData<Boolean>
        get() = _shiftAvailable

    private val _retryAvailable = MutableLiveData<Boolean>()
    val retryAvailable: LiveData<Boolean>
        get() = _retryAvailable

    fun disableShiftButton() {
        location = null
        updateShiftButton()
    }

    internal var location: Location? = null
    fun setLocation(location: Location) {
        this.location = location

        _shiftAvailable.value = (canStart() || canEnd()) && _shiftButton.value !== UserStatus.LOADING
    }

    fun loadShifts() {
        _retryAvailable.value = false
        _dataLoading.value = true
        _shiftButton.value = UserStatus.LOADING
        _shiftAvailable.value = false
        /*shiftRepository.getShifts(object : ShiftDataSource.LoadShiftCallback {
            override fun onShiftsLoaded(shifts: List<Shift>) {
                _dataLoading.value = false
                _retryAvailable.value = false
                _items.value = shifts
                updateShiftButton()
            }

            override fun onDataNotAvailable() {
                _retryAvailable.value = true
                _dataLoading.value = false
                updateShiftButton()
            }
        })*/
    }

    fun startShift() {
        if (location == null)
            return

        if (canStart()) {
            location?.also {
                _shiftButton.value = UserStatus.LOADING
                _shiftAvailable.value = false
                /*shiftRepository.startShift(
                    ShiftHalf(
                        getCurrentTime(),
                        it.latitude.toString(),
                        it.longitude.toString()
                    ),
                    object : ShiftDataSource.CompletableCallback {
                        override fun onComplete(shifts: List<Shift>) {
                            _items.value = shifts
                            _retryAvailable.value = false
                            updateShiftButton()
                        }

                        override fun onError(message: String?) {
                            //updateShiftButton()
                            message?.also { _snackbarString.value = Event(it) }
                        }
                    })*/
            }

        } else {
            showSnackbarInt(R.string.error_cannot_start_shift)
        }
    }

    fun endShift() {
        if (location == null)
            return

        if (canEnd()) {
            location?.also {
                _shiftButton.value = UserStatus.LOADING
                _shiftAvailable.value = false
                /*shiftRepository.endShift(
                    ShiftHalf(
                        getCurrentTime(),
                        it.latitude.toString(),
                        it.longitude.toString()
                    ),
                    object : ShiftDataSource.CompletableCallback {
                        override fun onComplete(shifts: List<Shift>) {
                            _items.value = shifts
                            _retryAvailable.value = false
                            updateShiftButton()
                        }

                        override fun onError(message: String?) {
                            //updateShiftButton()
                            message?.also { showSnackbarText(it) }
                        }
                    })*/
            }

        } else {
            showSnackbarInt(R.string.error_cannot_end_shift)
        }
    }

    fun getCurrentTime(): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        return df.format(Date())
    }

    private fun showSnackbarInt(@StringRes message: Int) {
        _snackbarInt.value = Event(message)
    }

    private fun showSnackbarText(message: String) {
        _snackbarString.value = Event(message)
    }

    fun updateShiftButton() {
        val canStart = canStart()
        val canEnd = canEnd()
        if (canStart) {
            _shiftButton.value = UserStatus.READY

        } else if (canEnd) {
            _shiftButton.value = UserStatus.WORKING
        }

        _shiftAvailable.value =
            location != null
                    && (canStart || canEnd)
    }

    fun canStart() =
        _items.value == null
                || _items.value!!.isEmpty()
                || !_items.value!!.last().end.isEmpty()

    fun canEnd() =
        _items.value != null
                && !_items.value!!.isEmpty()
                && _items.value!!.last().end.isEmpty()

    fun moveToDetail(shift: Shift) {

    }
}