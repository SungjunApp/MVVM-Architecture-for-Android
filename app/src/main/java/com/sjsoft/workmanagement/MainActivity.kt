package com.sjsoft.workmanagement

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }



    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnCanceledListener {
                Log.w(TAG, "addOnCanceledListener")
            }
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    task.result?.also {
                        model.setLocation(it)
                        Log.w(TAG, "getLastLocation\tlatitude: $it.latitude, longitude: $it.longitude")

                    }

                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                    showSnackbar(R.string.no_location_detected)
                    model.disableShiftButton()
                }
            }
    }

    private fun checkPermissions() =
        ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
            // Provide an additional rationale to the user. This would happen if the user denied the
            // request previously, but didn't check the "Don't ask again" checkbox.
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar(R.string.permission_rationale, android.R.string.ok, View.OnClickListener {
                // Request permission
                startLocationPermissionRequest()
            })

        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                grantResults.isEmpty() -> Log.i(TAG, "User interaction was cancelled.")

                // Permission granted.
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLastLocation()

                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                else -> {
                    showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent().apply {
                                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                    data = Uri.fromParts("package", APPLICATION_ID, null)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(intent)
                        })
                }
            }
        }
    }


    private fun showSnackbar(
        snackStrId: Int,
        actionStrId: Int = 0,
        listener: View.OnClickListener? = null
    ) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), getString(snackStrId),
            LENGTH_INDEFINITE)
        if (actionStrId != 0 && listener != null) {
            snackbar.setAction(getString(actionStrId), listener)
        }
        snackbar.show()
    }

}
