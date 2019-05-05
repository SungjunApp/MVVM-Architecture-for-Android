package com.sjsoft.app.ui.register

import com.sjsoft.app.navigator.Navigator
import com.sjsoft.app.model.register.DRequestToken
import com.sjsoft.app.ui.HomeFragment


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