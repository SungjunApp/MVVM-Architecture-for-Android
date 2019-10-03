package com.sjsoft.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.sjsoft.app.R
import com.sjsoft.app.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : BaseFragment() {
    companion object {
        const val TAB_INDEX = "TAB_INDEX"
        fun openithTrading(): TabFragment {
            return getInstance(R.id.nav_trading)
        }

        fun openMyAsset(): TabFragment {
            return getInstance(R.id.nav_my_asset)
        }

        fun openTx(): TabFragment {
            return getInstance(R.id.nav_tx)
        }

        fun openAccount(): TabFragment {
            return getInstance(R.id.nav_account)
        }

        private fun getInstance(index: Int): TabFragment {
            val fragment = TabFragment()
            val bundle = Bundle()
            bundle.putInt(TAB_INDEX, index)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            menuId = it.getInt(TAB_INDEX)
        }

    }

    override val titleResource: Int
        get() = R.string.title_main

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    private var rFragmentManager: FragmentManager? = null
    private var menuId: Int = 0
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rFragmentManager = childFragmentManager
        rFragmentManager?.addOnBackStackChangedListener(mOnBackStackChangedListener)

        savedInstanceState?.also {
            menuId = it.getInt(TAB_INDEX)
        }

        if (menuId == 0)
            menuId = R.id.nav_trading

        bottom_navigation.setOnNavigationItemSelectedListener {
            menuId = it.itemId
            when (it.itemId) {
                /*R.id.nav_trading -> {
                    setFragment(TradeHomeFragment())
                }
                R.id.nav_my_asset -> {
                    setFragment(MyAssetFragment())
                }
                R.id.nav_tx -> {
                    setFragment(TradeHomeFragment())
                }
                R.id.nav_account -> {
                    setFragment(AccountFragment())
                }*/

            }
            true
        }

        bottom_navigation.selectedItemId = menuId
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAB_INDEX, menuId)
    }

    private var rFragment: BaseFragment? = null
    private fun setFragment(baseFragment: BaseFragment) {
        rFragment = baseFragment
        replaceFragmentInActivity(baseFragment)
    }

    fun getFragmentcount(): Int {
        return if (rFragmentManager != null) rFragmentManager!!.backStackEntryCount else 0
    }

    private val mOnBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        try {
            val fragmentCount = rFragmentManager!!.backStackEntryCount
            if (fragmentCount > 0) {

                val backEntry = rFragmentManager!!.getBackStackEntryAt(getFragmentcount() - 1)
                val fragment = rFragmentManager!!.findFragmentByTag(backEntry.name)
                rFragment = fragment as BaseFragment
//                †    (activity as MainActivity).changeColorTheme(rFragment)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}