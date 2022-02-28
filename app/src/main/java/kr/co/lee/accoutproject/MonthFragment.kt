package kr.co.lee.accoutproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.adapters.MonthRecyclerAdapter
import kr.co.lee.accoutproject.calendar.CustomCalendarView
import kr.co.lee.accoutproject.databinding.FragmentMonthBinding
import kr.co.lee.accoutproject.viewmodels.MainViewModel
import org.joda.time.LocalDate

@AndroidEntryPoint
class MonthFragment: Fragment() {
    private var _binding: FragmentMonthBinding? = null
    private val binding: FragmentMonthBinding
        get() = _binding!!

    // Fragment KTX를 사용하여 Activity ViewModel 초기화
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // DataBinding
        _binding = DataBindingUtil.inflate<FragmentMonthBinding>(
            inflater, R.layout.fragment_month, container, false)
        binding.apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MonthFragment

            // ClickHandler
            calendar.setEventHandler(object : CustomCalendarView.EventHandler {
                override fun onDayPress(localDate: LocalDate) {
                    if(mainViewModel.date.value?.monthOfYear == localDate.minusMonths(1).monthOfYear) { // 이전달 클릭
                        nextButtonClick() 
                    } else if(mainViewModel.date.value?.monthOfYear == localDate.plusMonths(1).monthOfYear) { // 다음달 클릭
                        prevButtonClick()
                    }
                    mainViewModel.setDate(localDate)
                    if(mainViewModel.dateAccounts.value?.get(localDate) == null) {
                        binding.monthRecycler.visibility = View.INVISIBLE
                        binding.tvNoItem.visibility = View.VISIBLE
                    } else {
                        val recyclerAdapter = MonthRecyclerAdapter(mainViewModel.dateAccounts.value?.get(localDate)!!)
                        binding.monthRecycler.adapter = recyclerAdapter
                        binding.monthRecycler.visibility = View.VISIBLE
                        binding.tvNoItem.visibility = View.INVISIBLE
                    }
                }
            })
        }

        firstInit()
        subscribeUi()

        return binding.root
    }

    fun prevButtonClick() {
        val date = binding.calendar.prevButtonClick()
        mainViewModel.setDate(date)
        mainViewModel.setAccount()
    }

    fun nextButtonClick() {
        val date = binding.calendar.nextButtonClick()
        mainViewModel.setDate(date)
        mainViewModel.setAccount()
    }

    private fun firstInit() {
        // FirstInit
        mainViewModel.setDate(binding.calendar.setFirstDate())
        mainViewModel.setAccount()
    }

    private fun subscribeUi() {
        mainViewModel.accounts.observe(viewLifecycleOwner) {
            mainViewModel.setDateAccounts()
        }

        mainViewModel.dateAccounts.observe(viewLifecycleOwner) {
            binding.calendar.updateCalendar(mainViewModel.dateAccounts.value)
//            val recyclerAdapter = MonthRecyclerAdapter(it[mainViewModel.date.value]!!)
//            binding.monthRecycler.adapter = recyclerAdapter
        }
    }
}