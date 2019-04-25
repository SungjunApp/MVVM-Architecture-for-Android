package com.bluewhale.sa.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {
    abstract val titleResource: Int

    protected var _disposables: CompositeDisposable? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _disposables = CompositeDisposable()
    }

    override fun onDestroyView() {
        val inputManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (activity!!.currentFocus != null && activity!!.currentFocus!!.windowToken != null)
            inputManager.hideSoftInputFromWindow(
                activity!!.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

        if (_disposables != null) {
            _disposables!!.clear()
            _disposables!!.dispose()
        }
        super.onDestroyView()
    }
}