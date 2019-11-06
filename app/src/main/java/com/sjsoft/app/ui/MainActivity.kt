package com.sjsoft.app.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.sjsoft.app.BuildConfig
import com.sjsoft.app.R
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.ui.main.MenuFragment
import com.sjsoft.app.util.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    val frameLayoutId = R.id.contentFrame

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.navigationIcon?.setColorFilter(
            ContextCompat.getColor(this, R.color.grey_60),
            PorterDuff.Mode.SRC_ATOP
        )
        setSystemBarColor(R.color.grey_5)
        setSystemBarLight()

        if (AppConfig.needDebugInfo()) {
            toolbar.setSubtitleTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            toolbar.setTitleTextAppearance(this, R.style.TextAppearance_AppCompat_Medium)
            toolbar.setSubtitleTextAppearance(this, R.style.TextAppearance_AppCompat_Caption)
        }

        supportFragmentManager.addOnBackStackChangedListener(mOnBackStackChangedListener)

        goToFirstScreen(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        goToFirstScreen(intent)
    }


    fun goToFirstScreen(intent: Intent?) {
        //로또 회차 조회 : 임시저장 -> HomeFragment -> UploadFragment -> 임시저장 삭제
        intent?.data?.also { uri ->
            uri.queryDeepLinkParam(AppConfig.DeepLinkParams.drwNo)?.also{
                viewModel.saveReservedDrwNo(it)
                Timber.d("onCreate() -> deep link drwNo: $it")
            }
        }

        replaceFragmentInActivity(frameLayoutId, MenuFragment())
    }

    private val mOnBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        try {
            var showToolbar = true
            val fragmentCount = supportFragmentManager.backStackEntryCount
            val title: String?
            title =
                if (fragmentCount > 0) {
                    val fragment =
                        supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1] as BaseFragment
                    if (fragment is MenuFragment) showToolbar = false

                    fragment.getCustomTitle() ?: getString(fragment.titleResource)
                } else
                    getString(R.string.app_name)

            if (AppConfig.needDebugInfo())
                supportActionBar?.subtitle =
                    "Server: ${BuildConfig.FLAVOR},\tVerName: ${getAppVersionName()}, VerCode: ${getAppVersionCode()}"

            Timber.d("fragmentCount: $fragmentCount")
            setupActionBar(toolbar) {
                if (showToolbar) show() else hide()
                setTitle(title)
                setDisplayHomeAsUpEnabled(fragmentCount > 1)
                setDisplayShowHomeEnabled(fragmentCount > 1)
            }

            invalidateOptionsMenu()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val f = getCurrentFragment()
        f?.also { f.onActivityResult(requestCode, resultCode, data) }
    }
}
