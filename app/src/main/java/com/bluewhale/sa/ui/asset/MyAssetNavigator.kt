package com.bluewhale.sa.ui.asset

import com.bluewhale.sa.navigator.Navigator
import com.bluewhale.sa.ui.HomeFragment


class MyAssetNavigator constructor(val navi: Navigator) {
    fun goRootFragment() {
        navi.goRootFragment()
    }

    fun goVoteFragment(tradeId: String) {
        //todo : goto vote
    }

    fun goTradeDetailFragment(tradeId: String) {
        //todo : goto trade detail
    }

    fun goHomeFragment() {
        navi.replaceFragment(HomeFragment.getInstance())
    }
}