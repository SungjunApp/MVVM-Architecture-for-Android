package com.bluewhale.sa.ui.shift

import android.app.Application
import android.location.Location
import android.location.LocationManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bluewhale.sa.LiveDataTestUtil
import com.bluewhale.sa.R
import com.bluewhale.sa.data.Shift
import com.bluewhale.sa.data.ShiftHalf
import com.bluewhale.sa.data.source.ShiftDataSource
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.ui.shift.work.UserStatus
import com.bluewhale.sa.util.any
import com.bluewhale.sa.util.capture
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

    @Test
    fun startShift_started() {
        val shiftHalf = ShiftHalf(
            latitude = latitude,
            longitude = longitude
        )

        shiftViewModel.setLocation(Location(LocationManager.NETWORK_PROVIDER))
        shiftViewModel.startShift()

        Mockito.verify<ShiftRepository>(shiftRepository).startShift(any(), capture(editShiftsCallbackCaptor))
        Assert.assertSame(LiveDataTestUtil.getValue(shiftViewModel.shiftButton), UserStatus.LOADING)

        var shifts = mutableListOf(
            makeShift(
                "1",
                "2016-05-23T00:00:01+00:00"
            )
        )
        editShiftsCallbackCaptor.value.onComplete(shifts)

        Assert.assertSame(LiveDataTestUtil.getValue(shiftViewModel.shiftButton), UserStatus.WORKING)
    }

    @Test
    fun startShift_duplicationError() {
        val shift1 = makeShift(
            "2",
            "2016-05-22T00:00:00+00:00",
            "2016-05-22T09:30:00+00:00"
        )

        val shift2 = makeShift(
            "1",
            "2016-05-21T00:00:00+00:00"
        )

        var shifts = mutableListOf(shift1, shift2)

        shiftViewModel.setLocation(Location(LocationManager.NETWORK_PROVIDER))
        shiftViewModel.loadShifts()

        // Callback is captured and invoked with stubbed tasks
        Mockito.verify<ShiftRepository>(shiftRepository).getShifts(capture(loadShiftsCallbackCaptor))

        // Then progress indicator is shown
        Assert.assertTrue(LiveDataTestUtil.getValue(shiftViewModel.dataLoading))
        loadShiftsCallbackCaptor.value.onShiftsLoaded(shifts)

        // Then progress indicator is hidden
        Assert.assertFalse(LiveDataTestUtil.getValue(shiftViewModel.dataLoading))

        // And data loaded
        Assert.assertFalse(LiveDataTestUtil.getValue(shiftViewModel.items).isEmpty())
        Assert.assertTrue(LiveDataTestUtil.getValue(shiftViewModel.items).last().end.isEmpty())

        shiftViewModel.startShift()

        val value = LiveDataTestUtil.getValue(shiftViewModel.snackbarInt)
        Assert.assertEquals(
            value.getContentIfNotHandled(),
            R.string.error_cannot_start_shift
        )
    }

    @Test
    fun endShift_ended() {

        val shift1 = makeShift(
            "2",
            "2016-05-22T00:00:00+00:00",
            "2016-05-22T09:30:00+00:00"
        )
        val shift2 = makeShift(
            "1",
            "2016-05-21T00:00:00+00:00"
            //"2016-05-21T09:30:00+00:00"
        )

        var shifts = mutableListOf(shift1, shift2)

        shiftViewModel.setLocation(Location(LocationManager.NETWORK_PROVIDER))
        shiftViewModel.loadShifts()

        // Callback is captured and invoked with stubbed tasks
        Mockito.verify<ShiftRepository>(shiftRepository).getShifts(capture(loadShiftsCallbackCaptor))

        // Then progress indicator is shown
        Assert.assertTrue(LiveDataTestUtil.getValue(shiftViewModel.dataLoading))
        loadShiftsCallbackCaptor.value.onShiftsLoaded(shifts)

        // Then progress indicator is hidden
        Assert.assertFalse(LiveDataTestUtil.getValue(shiftViewModel.dataLoading))

        // And data loaded
        Assert.assertFalse(LiveDataTestUtil.getValue(shiftViewModel.items).isEmpty())
        Assert.assertTrue(LiveDataTestUtil.getValue(shiftViewModel.items).last().end.isEmpty())

        shiftViewModel.endShift()

        Mockito.verify<ShiftRepository>(shiftRepository).endShift(any(), capture(editShiftsCallbackCaptor))
        Assert.assertSame(LiveDataTestUtil.getValue(shiftViewModel.shiftButton), UserStatus.LOADING)

        shifts.last().end = "2016-05-21T09:30:00+00:00"
        shifts.last().endLatitude = latitude
        shifts.last().endLongitude = longitude

        editShiftsCallbackCaptor.value.onComplete(shifts)

        Assert.assertSame(LiveDataTestUtil.getValue(shiftViewModel.shiftButton), UserStatus.READY)
    }
}
