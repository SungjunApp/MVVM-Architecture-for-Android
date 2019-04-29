package com.bluewhale.sa

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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.bluewhale.sa.BuildConfig.APPLICATION_ID
import com.bluewhale.sa.model.DWallet
import com.bluewhale.sa.ui.BaseFragment
import com.bluewhale.sa.ui.shift.ShiftViewModel
import com.bluewhale.sa.ui.shift.work.WorkFragment
import com.bluewhale.sa.util.KS
import com.bluewhale.sa.view.replaceFragmentInActivity
import com.bluewhale.sa.view.setupActionBar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.ethereum.geth.Geth
import org.ethereum.geth.KeyStore
import java.io.File
import java.security.SecureRandom


class MainActivity : AppCompatActivity() , HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): AndroidInjector<Fragment>  = fragmentInjector

    @Inject
    lateinit var factory: ViewModelFactory

    val TAG = "MainActivity"

    @Inject lateinit var dWallet: DWallet

    lateinit var model: ShiftViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        model = ViewModelProviders.of(this, factory)
            .get(ShiftViewModel::class.java)

        model.disableShiftButton()
        println("wallet.address: ${dWallet.address}")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        supportFragmentManager.addOnBackStackChangedListener(mOnBackStackChangedListener)

        replaceFragmentInActivity(R.id.contentFrame, findOrCreateViewFragment())




    }

    private fun findOrCreateViewFragment() =
        supportFragmentManager.findFragmentById(R.id.contentFrame) ?: WorkFragment.newInstance()

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

    fun goToRootFragment(){
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
        ActivityCompat.requestPermissions(
            this, arrayOf(ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
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
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content), getString(snackStrId),
            LENGTH_INDEFINITE
        )
        if (actionStrId != 0 && listener != null) {
            snackbar.setAction(getString(actionStrId), listener)
        }
        snackbar.show()
    }

}
