package com.bluewhale.sa.ui.register


data class RegisterAgreementData(
    var clause1: Boolean,
    var clause2: Boolean,
    var clause3: Boolean
) {
    fun isPassable():Boolean{
        return clause1 && clause2
    }
}