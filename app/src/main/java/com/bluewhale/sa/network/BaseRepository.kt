package com.libs.meuuslibs.network

import com.bluewhale.sa.navigator.BaseSchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseRepository constructor(val navi: BaseSchedulerProvider) {
    interface SingleProvider<T> {
        fun onService(it: T): T
    }

    fun <T> makeSingleResponse(service: Single<T>, customResponse: SingleProvider<T>? = null): Single<T> {
        return service
            .subscribeOn(navi.io())
            .map {
                if (customResponse != null)
                    customResponse.onService(it)
                else it
            }.observeOn(navi.ui())
    }

    fun makeCompletableResponse(service: Completable): Completable {
        return service
            .subscribeOn(navi.io())
            .observeOn(navi.ui())
    }
}