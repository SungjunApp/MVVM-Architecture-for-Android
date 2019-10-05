package com.sjsoft.app.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sjsoft.app.R
import com.sjsoft.app.di.Injectable
import com.sjsoft.app.data.LottoMatch
import com.sjsoft.app.ui.BaseFragment
import com.sjsoft.app.ui.history.HistoryFragment
import com.sjsoft.app.ui.search.SearchFragment
import com.sjsoft.app.ui.trend.TrendFragment
import com.sjsoft.app.util.*
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), Injectable {
    override val titleResource: Int
        get() = R.string.title_main

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.generateButton.observe(this, Observer {
            bt_generate.isEnabled = it
        })

        viewModel.errorPopup.observe(this, Observer {
            showAlertDialog(getString(it))
        })

        viewModel.lotto.observe(this, Observer {
            v_result.apply {
                if (it.isEmpty())
                    hide()
                else
                    show()
            }

            tv_lotto.text = it
        })

        viewModel.lottoMatchUI.observe(this, Observer {
            handleLottoMatch(it)
        })

        viewModel.reservedDrwNo.observe(this, Observer {
            addFragmentToActivity(SearchFragment.getInstance(it))
        })


        bt_generate.setOnClickListener {
            viewModel.generateLotto()
        }

        bt_match.setSafeOnClickListener {
            viewModel.matchMineWithOfficial()
        }

        bt_history.setSafeOnClickListener {
            addFragmentToActivity(HistoryFragment())
        }

        bt_trend.setSafeOnClickListener {
            addFragmentToActivity(TrendFragment())
        }

        tv_event_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.eventNumber = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        viewModel.queryReservedDrwNo()
    }

    private fun handleLottoMatch(it: HomeViewModel.LottoMatchUI) {
        when (it) {
            is HomeViewModel.LottoMatchUI.Loading -> {
                v_loading.apply {
                    if (it.showing) show() else hide()
                }
                changeVisibility(bt_match, !it.showing)
            }
            is HomeViewModel.LottoMatchUI.Data -> {
                v_loading.hide()
                changeVisibility(bt_match, true)
                showLotteryResult(it.data)
            }
        }
    }

    private fun changeVisibility(view:View, isShowing:Boolean){
        view.apply {
            visibility = if(isShowing) VISIBLE else INVISIBLE
            isEnabled = isShowing
        }
    }

    private fun showLotteryResult(lottoMatch: LottoMatch) {
        context?.let {
            val builder = AlertDialog.Builder(it)
            val poupMessage = lottoMatch.getPopupMessage(it)
            builder.setTitle(poupMessage.title)
            builder.setMessage(poupMessage.message)
            builder.setPositiveButton(android.R.string.ok) { _, _ ->

            }
            builder.show()
        }
    }

    companion object {
        fun getInstance(): HomeFragment {
            val f = HomeFragment()
            val bundle = Bundle()
            f.arguments = bundle
            return f
        }
    }
}