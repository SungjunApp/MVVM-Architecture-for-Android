package com.bluewhale.sa.ui.register

import com.bluewhale.sa.navigator.Navigator
import com.bluewhale.sa.data.source.register.DRequestToken
import com.bluewhale.sa.ui.HomeFragment


class RegisterNavigator constructor(val navi: Navigator) {
    fun goRootFragment() {
        navi.goRootFragment()
    }

    fun goRegisterInfoFragment(marketingClause: Boolean) {
        navi.addFragment(RegisterInfoFragment.getInstance(marketingClause))
    }

    fun goRegisterSMSFragment(marketingClause: Boolean, requestToken: DRequestToken) {
        navi.addFragment(RegisterSMSFragment.getInstance(marketingClause, requestToken))
    }

    fun goHomeFragment() {
        navi.replaceFragment(HomeFragment.getInstance())
    }
}