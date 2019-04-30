package com.bluewhale.sa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.bluewhale.sa.R
import com.bluewhale.sa.ui.trade.TradeHomeFragment
import com.bluewhale.sa.view.replaceFragmentInActivity
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment: BaseFragment() {
    companion object{
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

    override val titleResource: Int
        get() = R.string.title_history

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    private var rFragmentManager: FragmentManager? = null
    private var menuId: Int = 0
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rFragmentManager = childFragmentManager
        rFragmentManager?.addOnBackStackChangedListener(mOnBackStackChangedListener)

        menuId = when (arguments!!.getInt(TAB_INDEX)) {
            R.id.nav_trading -> 0
            R.id.nav_my_asset -> 1
            R.id.nav_tx -> 2
            R.id.nav_account -> 3
            else -> 0
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            menuId = it.itemId
            when (it.itemId) {
                R.id.nav_trading -> {
                    (activity as MainActivity).replaceFragmentInActivity(R.id.fragment_main_tab, TradeHomeFragment())
                }
                R.id.nav_my_asset -> {
                    setFragment(TradeHomeFragment())
                }
                R.id.nav_tx -> {
                    setFragment(TradeHomeFragment())
                }
                R.id.nav_account -> {
                    setFragment(TradeHomeFragment())
                }

            }
            true
        }

        bottom_navigation.selectedItemId = menuId
    }



    private var rFragment: BaseFragment? = null
    private fun setFragment(baseFragment: BaseFragment) {
        rFragment = baseFragment
        (activity as MainActivity).replaceFragmentInActivity(R.id.fragment_main_tab, baseFragment)
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