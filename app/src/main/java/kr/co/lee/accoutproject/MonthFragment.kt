package kr.co.lee.accoutproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import kr.co.lee.accoutproject.adapters.MonthRecyclerViewAdapter
import kr.co.lee.accoutproject.calendar.CalendarAdapter
import kr.co.lee.accoutproject.calendar.CustomCalendarView
import kr.co.lee.accoutproject.databinding.FragmentMonthBinding
import kr.co.lee.accoutproject.viewmodels.MainViewModel
import org.joda.time.DateTime
import org.joda.time.LocalDate
import javax.inject.Inject

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
    ): View? {
        // DataBinding
        _binding = DataBindingUtil.inflate<FragmentMonthBinding>(
            inflater, R.layout.fragment_month, container, false)
        binding.apply {
            viewModel = mainViewModel

            // ClickHandler
            calendar.setEventHandler(object : CustomCalendarView.EventHandler {
                override fun onDayPress(localDate: LocalDate) {
                    mainViewModel.setDate(localDate)
                    if(mainViewModel.dateAccounts.value?.get(localDate) == null) {
                        binding.monthRecycler.visibility = View.INVISIBLE
                        binding.tvNoItem.visibility = View.VISIBLE
                    } else {
                        val recyclerAdapter = MonthRecyclerViewAdapter(mainViewModel.dateAccounts.value?.get(localDate)!!)
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
        mainViewModel.setAccounts()
//        binding.calendar.updateCalendar(mainViewModel.dateAccounts.value)
    }

    fun nextButtonClick() {
        val date = binding.calendar.nextButtonClick()
        mainViewModel.setDate(date)
        mainViewModel.setAccounts()
//        binding.calendar.updateCalendar(mainViewModel.dateAccounts.value)
    }

    fun firstInit() {
        // FirstInit
        mainViewModel.setDate(binding.calendar.setFirstDate())
        mainViewModel.setAccounts()
        binding.calendar.updateCalendar(mainViewModel.dateAccounts.value)
    }

    fun subscribeUi() {
        mainViewModel.incomeMoney.observe(viewLifecycleOwner) {
            binding.incomeView.incomeText.text = it.toString()
        }

        mainViewModel.depositMoney.observe(viewLifecycleOwner) {
            binding.depositView.incomeText.text = it.toString()
        }

        mainViewModel.dateAccounts.observe(viewLifecycleOwner) {
            binding.calendar.updateCalendar(mainViewModel.dateAccounts.value)
        }
    }
}