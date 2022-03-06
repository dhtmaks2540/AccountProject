package kr.co.lee.accoutproject.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.base.BaseFragment
import kr.co.lee.accoutproject.ui.adapter.WeekRecyclerAdapter
import kr.co.lee.accoutproject.databinding.FragmentWeekBinding
import kr.co.lee.accoutproject.ui.main.MainViewModel

@AndroidEntryPoint
class WeekFragment: BaseFragment<FragmentWeekBinding>(R.layout.fragment_week) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var weekRecyclerAdapter: WeekRecyclerAdapter

    override fun initView() {
        binding.apply {
            viewModel = mainViewModel
        }

        initUi()
        subscribeUi()
    }

    fun prevButtonClick() {
        mainViewModel.setDate(mainViewModel.date.value?.minusMonths(1)!!)
    }

    fun nextButtonClick() {
        mainViewModel.setDate(mainViewModel.date.value?.plusMonths(1)!!)
    }

    private fun initUi() {
        mainViewModel.setWeeksAccounts()
        weekRecyclerAdapter = WeekRecyclerAdapter()
        binding.rvWeek.adapter = weekRecyclerAdapter
    }

    private fun subscribeUi() {
        mainViewModel.date.observe(viewLifecycleOwner) {
            mainViewModel.setAccount()
        }

        mainViewModel.accounts.observe(viewLifecycleOwner) {
            mainViewModel.setWeeksAccounts()
        }

        mainViewModel.weekAccounts.observe(viewLifecycleOwner) {
            weekRecyclerAdapter.add(it)
        }
    }

    companion object {
        fun newInstance(title: String) = WeekFragment().apply{
            arguments = Bundle().apply {
                putString("title", title)
            }
        }
    }
}