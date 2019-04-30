package com.bluewhale.sa.navigator

import android.app.Activity
import androidx.fragment.app.Fragment
import com.bluewhale.sa.ui.MainActivity
import com.bluewhale.sa.view.addFragmentToActivity
import com.bluewhale.sa.view.replaceFragmentInActivity
import com.kenai.jffi.Main
import java.lang.ref.WeakReference

class BaseNavigator(activity: Activity) : Navigator {
    private var mActivity: WeakReference<Activity> = WeakReference(activity)

    override fun addFragment(fragment: Fragment) {
        if(mActivity.get() is MainActivity){
            val mainActivity = mActivity.get() as MainActivity
            mainActivity.addFragmentToActivity(mainActivity.frameLayoutId, fragment)
        }
    }

    override fun replaceFragment(fragment: Fragment) {
        if(mActivity.get() is MainActivity) {
            val mainActivity = mActivity.get() as MainActivity
            mainActivity.replaceFragmentInActivity(mainActivity.frameLayoutId, fragment)
        }
    }

    override fun popFragment() {
        val mainActivity = mActivity.get()
        mainActivity?.onBackPressed()
    }

    override fun goRootFragment() {
        if(mActivity.get() is MainActivity){
            val mainActivity = mActivity.get() as MainActivity
            mainActivity.goToRootFragment()
        }
    }

}