package com.bluewhale.sa.navigator

import android.app.Activity
import androidx.fragment.app.Fragment
import com.bluewhale.sa.ui.MainActivity
import com.bluewhale.sa.view.addFragmentToActivity
import com.bluewhale.sa.view.replaceFragmentInActivity
import java.lang.ref.WeakReference

class BaseNavigator(activity: Activity) : Navigator {
    private var mActivity: WeakReference<Activity> = WeakReference(activity)

    override fun addFragment(fragment: Fragment) {
        val mainActivity = mActivity.get() as MainActivity
        mainActivity.addFragmentToActivity(fragment.id, fragment)
    }

    override fun replaceFragment(fragment: Fragment) {
        val mainActivity = mActivity.get() as MainActivity
        mainActivity.replaceFragmentInActivity(fragment.id, fragment)
    }

    override fun popFragment() {
        val mainActivity = mActivity.get() as MainActivity
        mainActivity.onBackPressed()
    }

    override fun goRootFragment() {
        val mainActivity = mActivity.get() as MainActivity
        mainActivity.goToRootFragment()
    }

}