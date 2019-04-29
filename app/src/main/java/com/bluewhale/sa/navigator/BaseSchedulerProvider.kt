package com.bluewhale.sa.navigator

import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull

interface BaseSchedulerProvider{
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}