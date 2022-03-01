package kr.co.lee.accoutproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.adapters.WeekRecyclerAdapter
import kr.co.lee.accoutproject.databinding.FragmentWeekBinding
import kr.co.lee.accoutproject.viewmodels.MainViewModel

@AndroidEntryPoint
class WeekFragment: Fragment() {
    private var _binding: FragmentWeekBinding? = null
    private val binding: FragmentWeekBinding
        get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var weekRecyclerAdapter: WeekRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_week, container, false)

        binding.apply {
            lifecycleOwner = this@WeekFragment
            viewModel = mainViewModel
        }

        initUi()
        subscribeUi()

        return binding.root
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
        fun newInstance(title: String) = MonthFragment().apply{
            arguments = Bundle().apply {
                putString("title", title)
            }
        }
    }
}