package com.sjsoft.app.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.usage.UsageStatsManager
import android.app.usage.UsageStatsManager.INTERVAL_YEARLY
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sjsoft.app.BuildConfig.APPLICATION_ID
import com.sjsoft.app.R
import com.sjsoft.app.ui.register.LoginFragment
import com.sjsoft.app.view.replaceFragmentInActivity
import com.sjsoft.app.view.setupActionBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    val frameLayoutId = R.id.contentFrame

    /*@Inject
    lateinit var factory: ShiftViewModelFactory*/

    val TAG = "MainActivity"

    //@Inject lateinit var dWallet: DWallet

    //lateinit var model: ShiftViewModel

    /*private val model: ShiftViewModel by lazyInject {
        ViewModelProviders.of(this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return ShiftViewModel(
                        Injection.provideShiftRepository(application)
                    ) as T
                }
            }).get(ShiftViewModel::class.java)
    }*/

    fun getForegroundPackageName(): String {
        val sb = StringBuilder()
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val endTime = System.currentTimeMillis()
        val beginTime = endTime-10000
        /*val usageEvents = usageStatsManager.queryEvents(0, endTime)
        while (usageEvents.hasNextEvent()) {
            val event = UsageEvents.Event()
            usageEvents.getNextEvent(event)
            if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                sb.append("name:${event.packageName}, timeStamp: ${event.timeStamp}").append("\n")
            }
        }*/
        usageStatsManager
        val usageEvents = usageStatsManager.queryUsageStats(INTERVAL_YEARLY, 0, endTime)
        for(usage in usageEvents){
            usage.packageName
            sb.append("name:${usage.packageName}, totalTime: ${usage.totalTimeInForeground}").append("\n")
            usage.totalTimeInForeground
        }

        return sb.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val test = getForegroundPackageName()
        //startActivityForResult(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 1)
        //Log.e("test hong","test hong:" +  test)

        supportFragmentManager.addOnBackStackChangedListener(mOnBackStackChangedListener)

        replaceFragmentInActivity(frameLayoutId, findOrCreateViewFragment())
    }

    private fun findOrCreateViewFragment() =
        supportFragmentManager.findFragmentById(R.id.contentFrame) ?: LoginFragment()
//        supportFragmentManager.findFragmentById(R.id.contentFrame) ?: TabFragment.openithTrading()

    private val mOnBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        try {
            val fragmentCount = supportFragmentManager.backStackEntryCount
            val title: String
            if (fragmentCount > 1) {
                val backEntry = supportFragmentManager.getBackStackEntryAt(fragmentCount - 1)
                val fragment = supportFragmentManager.findFragmentByTag(backEntry.name) as BaseFragment
                title = getString(fragment.titleResource)
            } else
                title = getString(R.string.app_name)

            if (fragmentCount > 1) {
                setupActionBar(toolbar) {
                    setTitle(title)
                    setDisplayHomeAsUpEnabled(true)
                    setDisplayShowHomeEnabled(true)
                }
            } else {
                setupActionBar(toolbar) {
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

    fun goToRootFragment() {
        val count = supportFragmentManager.backStackEntryCount
        if (count >= 2) {
            val be = supportFragmentManager.getBackStackEntryAt(0)
            supportFragmentManager.popBackStack(be.id, 0)
        }
    }

    private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
        beginTransaction().apply {
            action()
        }.commitAllowingStateLoss()
    }

}
