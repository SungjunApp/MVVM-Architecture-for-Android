package com.sjsoft.app.ui.asset

import com.sjsoft.app.navigator.Navigator
import com.sjsoft.app.ui.HomeFragment


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