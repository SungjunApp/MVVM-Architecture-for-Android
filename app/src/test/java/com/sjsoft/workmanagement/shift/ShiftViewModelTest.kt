package com.sjsoft.workmanagement.shift

import android.app.Application
import android.location.Location
import android.location.LocationManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sjsoft.workmanagement.LiveDataTestUtil
import com.sjsoft.workmanagement.R
import com.sjsoft.workmanagement.data.Shift
import com.sjsoft.workmanagement.data.ShiftHalf
import com.sjsoft.workmanagement.data.source.ShiftDataSource
import com.sjsoft.workmanagement.data.source.ShiftRepository
import com.sjsoft.workmanagement.shift.work.UserStatus
import com.sjsoft.workmanagement.util.any
import com.sjsoft.workmanagement.util.capture
import com.sjsoft.workmanagement.util.eq
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

class ShiftViewModelTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var shiftRepository: ShiftRepository
    @Mock
    private lateinit var context: Application
    @Captor
    private lateinit var loadShiftsCallbackCaptor: ArgumentCaptor<ShiftDataSource.LoadShiftCallback>
    @Captor
    private lateinit var editShiftsCallbackCaptor: ArgumentCaptor<ShiftDataSource.CompletableCallback>
    private lateinit var shiftViewModel: ShiftViewModel

    val latitude = "40.741895"
    val longitude = "-73.989308"

    fun makeShift(id: String, start: String, end: String = ""): Shift {
        if (end.isEmpty()) {
            return Shift(
                id = id,
                start = start,
                startLatitude = latitude,
                startLongitude = longitude,
                image = "https://farm4.staticflickr.com/3937/15437546888_6a6f608e9c.jpg"
            )
        } else {
            return Shift(
                id = id,
                start = start,
                end = end,
                startLatitude = latitude,
                startLongitude = longitude,
                endLatitude = latitude,
                endLongitude = longitude,
                image = "https://farm4.staticflickr.com/3937/15437546888_6a6f608e9c.jpg"
            )
        }

    }

    @Before
    fun setupShiftViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        //setupContext()

        // Get a reference to the class under test
        shiftViewModel = ShiftViewModel(shiftRepository)
    }

    @Test
    fun loadAllShiftsFromRepository_dataLoaded() {
        // We initialise the tasks to 3, with one active and two completed
        val shift1 = makeShift(
            "1",
            "2016-05-21T00:00:00+00:00",
            "2016-05-21T09:30:00+00:00"
        )
        val shift2 = makeShift(
            "2",
            "2016-05-22T00:00:00+00:00",
            "2016-05-22T09:30:00+00:00"
        )
        val shift3 = makeShift(
            "3",
            "2016-05-23T00:00:00+00:00",
            "2016-05-23T09:30:00+00:00"
        )

        var shifts = mutableListOf(shift1, shift2, shift3)

        shiftViewModel.setLocation(Location(LocationManager.NETWORK_PROVIDER))
        shiftViewModel.loadShifts()

        // Callback is captured and invoked with stubbed tasks
        Mockito.verify<ShiftRepository>(shiftRepository).getShifts(capture(loadShiftsCallbackCaptor))

        // Then progress indicator is shown
        Assert.assertTrue(LiveDataTestUtil.getValue(shiftViewModel.dataLoading))
        Assert.assertSame(LiveDataTestUtil.getValue(shiftViewModel.shiftButton), UserStatus.LOADING)

        loadShiftsCallbackCaptor.value.onShiftsLoaded(shifts)

        // Then progress indicator is hidden
        Assert.assertFalse(LiveDataTestUtil.getValue(shiftViewModel.dataLoading))
        Assert.assertSame(LiveDataTestUtil.getValue(shiftViewModel.shiftButton), UserStatus.READY)

        // And data loaded
        Assert.assertFalse(LiveDataTestUtil.getValue(shiftViewModel.items).isEmpty())
        Assert.assertTrue(LiveDataTestUtil.getValue(shiftViewModel.items).size == 3)
    }
}
