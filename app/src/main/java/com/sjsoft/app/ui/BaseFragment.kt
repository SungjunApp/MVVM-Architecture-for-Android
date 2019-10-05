package com.sjsoft.app.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.sjsoft.app.R
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {
    abstract val titleResource: Int

    open fun getCustomTitle(): String? {
        return null
    }

    protected lateinit var disposables: CompositeDisposable

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        disposables = CompositeDisposable()
    }

    override fun onDestroyView() {
        val inputManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (activity!!.currentFocus != null && activity!!.currentFocus!!.windowToken != null)
            inputManager.hideSoftInputFromWindow(
                activity!!.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

        disposables.clear()
        disposables.dispose()

        super.onDestroyView()
    }

    var loadingDialog: AlertDialog? = null
    fun makeLoading(show: Boolean) {
        if (show) {
            if (loadingDialog != null && loadingDialog!!.isShowing)
                return

            context?.let {
                val builder = AlertDialog.Builder(it)
                val inflater = layoutInflater
                val dialogLayout = inflater.inflate(R.layout.dialog_progress, null)
                builder.setView(dialogLayout)
                builder.setCancelable(false)
                loadingDialog = builder.show()
            }

        } else {
            if (loadingDialog != null && loadingDialog!!.isShowing)
                loadingDialog?.dismiss()
        }
    }
}