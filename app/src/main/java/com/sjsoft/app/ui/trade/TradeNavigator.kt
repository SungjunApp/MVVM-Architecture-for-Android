package com.sjsoft.app.ui.trade

import com.sjsoft.app.navigator.Navigator
import com.sjsoft.app.ui.HomeFragment


class TradeNavigator constructor(val navi: Navigator) {
    fun goRootFragment() {
        navi.goRootFragment()
    }

    fun goTradeDetailFragment(tradeId: String) {
        navi.addFragment(TradeDetailFragment.getInstance(tradeId))
    }

    fun goHomeFragment() {
        navi.replaceFragment(HomeFragment.getInstance())
    }
}