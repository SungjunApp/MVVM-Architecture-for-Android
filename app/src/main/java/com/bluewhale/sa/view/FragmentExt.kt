package com.bluewhale.sa.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bluewhale.sa.R

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun Fragment.replaceFragmentInActivity(frameId: Int, fragment: Fragment) {
    childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    childFragmentManager.transact {
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
fun Fragment.addFragmentToActivity(frameId: Int, fragment: Fragment) {
    childFragmentManager.transact {
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