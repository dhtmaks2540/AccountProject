package kr.co.lee.accoutproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import kr.co.lee.accoutproject.adapters.MonthRecyclerViewAdapter
import kr.co.lee.accoutproject.calendar.CalendarAdapter
import kr.co.lee.accoutproject.databinding.FragmentMonthBinding
import kr.co.lee.accoutproject.viewmodels.MainViewModel
import org.joda.time.DateTime
import javax.inject.Inject

@AndroidEntryPoint
class MonthFragment: Fragment() {
    private var _binding: FragmentMonthBinding? = null
    private val binding: FragmentMonthBinding
        get() = _binding!!

    private var timeCheck: Long = 0L
    private var prev_year = 0
    private var prev_month = 0
    private var prev_day = 0

    // FragmentStateAdapter
    private lateinit var calendarAdapter: CalendarAdapter

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
            
            // 처음 초기화
//            mainViewModel.setDate(DateTime(calendarAdapter.getItemId(0)))

            calendarAdapter = CalendarAdapter(requireActivity(), mainViewModel)
            // Set the currently selected page.
            // Int.MAX_VALUE를 반으로 나눈 값을 선택된 페이지로 초기화
            calendarPager.adapter = calendarAdapter
            calendarPager.setCurrentItem(CalendarAdapter.START_POSITION, false)
            calendarPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    mainViewModel.setDate(DateTime(calendarAdapter.getItemId(position)))
                    super.onPageSelected(position)
                }
            })

            // CalendarView
//            calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
//                mainViewModel.setDate(DateTime().withYear(year).withMonthOfYear(month + 1).withDayOfMonth(dayOfMonth))
//                if (prev_year != year || prev_month != month || prev_day != dayOfMonth || System.currentTimeMillis() > timeCheck + 1500) {
//                    timeCheck = System.currentTimeMillis()
//                    prev_year = year
//                    prev_month = month
//                    prev_day = dayOfMonth
//                    subscribeUi()
//                } else if(prev_year == year && prev_month == month && prev_day == dayOfMonth && System.currentTimeMillis() <= timeCheck + 1500) {
//                    // 프래그먼트 바꾸기(일간 프래그먼트로)
//                }
//            }
        }

        return binding.root
    }

    private fun subscribeUi() {
        mainViewModel.setAccounts()
        mainViewModel.accounts.observe(viewLifecycleOwner) {
            mainViewModel.setMoney()
            val recyclerAdapter = MonthRecyclerViewAdapter(it)
            binding.monthRecycler.adapter = recyclerAdapter
        }
    }
}