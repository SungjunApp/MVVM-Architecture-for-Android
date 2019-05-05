package com.sjsoft.app.view

import android.app.Activity
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sjsoft.app.R

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(frameId: Int, fragment: Fragment) {
    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    supportFragmentManager.transact {
        setCustomAnimations(0, 0, 0, 0)
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        addToBackStack(fragment.getIdentifier())
        replace(frameId, fragment, fragment.getIdentifier())
    }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(frameId: Int, fragment: Fragment) {
    supportFragmentManager.transact {
        setCustomAnimations(
            R.anim.slide_in_right_left,
            R.anim.slide_out_right_left,
            R.anim.slide_in_left_right,
            R.anim.slide_out_left_right
        )
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        addToBackStack(fragment.getIdentifier())
        replace(frameId, fragment, fragment.getIdentifier())
    }
}

fun AppCompatActivity.setupActionBar(toolbar: Toolbar, action: ActionBar.() -> Unit) {
    setSupportActionBar(toolbar)
    supportActionBar?.run {
        action()
    }
}

/*fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ShiftViewModelFactory.getInstance(application)).get(viewModelClass)*/

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun Fragment.getIdentifier():String{
    return this::class.java.canonicalName
}