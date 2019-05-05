package com.sjsoft.app.network.repository

import com.sjsoft.app.model.DWallet
import io.reactivex.Completable
import io.reactivex.Single

interface BlockChainRepository{
    fun generatePassword(): Single<String>
    fun createWallet():Single<DWallet>

    fun getPassword(wallet: DWallet): Single<String>
    fun setPassword(wallet: DWallet, password: String): Completable

}