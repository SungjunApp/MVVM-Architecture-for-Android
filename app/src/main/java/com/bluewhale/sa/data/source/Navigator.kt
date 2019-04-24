package com.bluewhale.sa.data.source

import androidx.fragment.app.Fragment

interface Navigator {
    /**
     * Push a fragment to FragmentStack
     *
     * @param fragment
     */
    fun addFragment(fragment: Fragment)

    /**
     * Clear backstack
     * then, set a fragment to first stack of FragmentStack
     *
     * @param fragment
     */
    fun replaceFragment(fragment: Fragment)

    /**
     * Replace top stack of FragmentStack
     * then, go to previous fragment
     */
    fun popFragment()

    /**
     * Replace all stacks of FragmentStack
     * then, go to first fragment
     */
    fun goRootFragment()
}