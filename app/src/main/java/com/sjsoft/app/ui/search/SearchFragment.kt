package com.sjsoft.app.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sjsoft.app.R
import com.sjsoft.app.di.Injectable
import com.sjsoft.app.ui.BaseFragment
import com.sjsoft.app.util.hide
import com.sjsoft.app.util.setSafeOnClickListener
import com.sjsoft.app.util.show
import com.sjsoft.app.util.showAlertDialog
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : BaseFragment(), Injectable {
    override val titleResource: Int
        get() = R.string.title_winning_numbers

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_title.text = getString(R.string.winning_numbers, getDrwNo())

        viewModel.errorPopup.observe(this, Observer {
            showAlertDialog(getString(it))
        })

        viewModel.lottoUI.observe(this, Observer {
            handleLottoUI(it)
        })

        v_retry.setSafeOnClickListener {
            v_retry.hide()
            viewModel.getWinner(getDrwNo())
        }

        viewModel.getWinner(getDrwNo())
    }

    private fun handleLottoUI(it: SearchViewModel.LottoUI) {
        when (it) {
            is SearchViewModel.LottoUI.Loading -> {
                v_loading.apply {
                    if (it.showing) show() else hide()
                }

                tv_lotto.apply {
                    if (!it.showing) show() else hide()
                }
            }
            is SearchViewModel.LottoUI.Data -> {
                v_retry.hide()
                v_loading.hide()
                tv_lotto.show()
                tv_lotto.text = it.data.getDisplayText()
            }
            is SearchViewModel.LottoUI.Failur -> {
                v_loading.hide()
                tv_lotto.hide()
                v_retry.show()
            }
        }
    }

    private fun getDrwNo(): Int {
        return arguments?.getInt("drwNo")!!
    }

    companion object {
        fun getInstance(drwNo: Int): SearchFragment {
            val f = SearchFragment()
            val bundle = Bundle()
            bundle.putInt("drwNo", drwNo)
            f.arguments = bundle
            return f
        }
    }
}