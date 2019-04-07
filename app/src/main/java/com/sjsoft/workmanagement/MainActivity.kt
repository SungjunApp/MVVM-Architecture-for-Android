package com.sjsoft.workmanagement

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import com.sjsoft.workmanagement.BuildConfig.APPLICATION_ID
import com.sjsoft.workmanagement.shift.BaseFragment
import com.sjsoft.workmanagement.shift.ShiftViewModel
import com.sjsoft.workmanagement.shift.work.WorkFragment

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var model: ShiftViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(ShiftViewModel::class.java)
        model.disableShiftButton()

        supportFragmentManager.addOnBackStackChangedListener(mOnBackStackChangedListener)

        replaceFragmentInActivity(R.id.contentFrame, findOrCreateViewFragment(), "WorkFragment")
    }

    private fun findOrCreateViewFragment() =
        supportFragmentManager.findFragmentById(R.id.contentFrame) ?: WorkFragment.newInstance()

    fun setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
        setSupportActionBar(findViewById(toolbarId))
        supportActionBar?.run {
            action()
        }
    }

    fun AppCompatActivity.replaceFragmentInActivity(frameId: Int, fragment: Fragment, tag: String) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.transact {
            setCustomAnimations(0, 0, 0, 0)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(tag)
            replace(frameId, fragment, tag)
        }
    }

    fun AppCompatActivity.addFragmentToActivity(frameId: Int, fragment: Fragment, tag: String) {
        supportFragmentManager.transact {
            setCustomAnimations(
                R.anim.slide_in_right_left,
                R.anim.slide_out_right_left,
                R.anim.slide_in_left_right,
                R.anim.slide_out_left_right
            )
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(tag)
            replace(frameId, fragment, tag)
        }
    }

    private val mOnBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        try {
            val fragmentCount = supportFragmentManager.backStackEntryCount
            var title = ""
            if (fragmentCount > 0) {
                val backEntry = supportFragmentManager.getBackStackEntryAt(fragmentCount - 1)
                val fragment = supportFragmentManager.findFragmentByTag(backEntry.name) as BaseFragment
                title = getString(fragment.titleResource)
            }

            if (fragmentCount > 1) {
                setupActionBar(R.id.toolbar) {
                    setTitle(title)
                    setDisplayHomeAsUpEnabled(true)
                    setDisplayShowHomeEnabled(true)
                }
            } else {
                setupActionBar(R.id.toolbar) {
                    setTitle(title)
                    setDisplayHomeAsUpEnabled(false)
                    setDisplayShowHomeEnabled(false)
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        try {
            val fragmentStackSize = supportFragmentManager.backStackEntryCount
            if (fragmentStackSize <= 1) {
                finish()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onBackPressed()
    }

    private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
        beginTransaction().apply {
            action()
        }.commitAllowingStateLoss()
    }



}
