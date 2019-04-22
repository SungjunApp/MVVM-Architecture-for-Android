package com.bluewhale.sa.ui.shift.work

enum class UserStatus {
    /**
     * There is no unfihished shift
     */
    READY,

    /**
     * This is the moment when the app is loading the shift data from the server
     */
    LOADING,

    /**
     * There is an unfinished shift
     */
    WORKING
}