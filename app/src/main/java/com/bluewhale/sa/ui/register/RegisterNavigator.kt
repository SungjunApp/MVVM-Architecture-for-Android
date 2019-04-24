package com.bluewhale.sa.ui.register

import com.bluewhale.sa.data.source.Navigator


class RegisterNavigator constructor(val navi: Navigator) {
    fun goRootFragment() {
        navi.goRootFragment()
    }

    fun goRegisterInfoFragment(marketingClause: Boolean) {
        navi.addFragment(RegisterInfoFragment.getInstance(marketingClause))
    }

    fun goRegisterSMSFragment(marketingClause: Boolean) {
        navi.addFragment(RegisterSMSFragment.getInstance(marketingClause))
    }
}