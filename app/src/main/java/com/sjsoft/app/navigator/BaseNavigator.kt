package com.sjsoft.app.navigator

import android.app.Activity
import androidx.fragment.app.Fragment
import com.sjsoft.app.ui.MainActivity
import com.sjsoft.app.view.addFragmentToActivity
import com.sjsoft.app.view.replaceFragmentInActivity
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