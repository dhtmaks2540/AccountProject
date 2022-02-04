package kr.co.lee.accoutproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.databinding.FragmentMonthBinding
import kr.co.lee.accoutproject.viewmodels.MainViewModel
import org.joda.time.DateTime

class MonthFragment : Fragment() {
    private var timeCheck: Long = 0L
    private var prev_year = 0
    private var prev_month = 0
    private var prev_day = 0

    // FragmentStateAdapter
//    private lateinit var calendarAdapter: CalendarAdapter
    // Fragment KTX를 사용하여 Activity ViewModel 초기화
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // DataBinding
        val binding = DataBindingUtil.inflate<FragmentMonthBinding>(
            inflater, R.layout.fragment_month, container, false)
            .apply {
                viewModel = mainViewModel

                mainViewModel.setDate(DateTime(calendar.date))

                // CalendarView
                calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
                    mainViewModel.setDate(DateTime().withYear(year).withMonthOfYear(month + 1).withDayOfMonth(dayOfMonth))
                    if (prev_year != year || prev_month != month || prev_day != dayOfMonth || System.currentTimeMillis() > timeCheck + 1500) {
                        timeCheck = System.currentTimeMillis()
                        prev_year = year
                        prev_month = month
                        prev_day = dayOfMonth
                    } else if(prev_year == year && prev_month == month && prev_day == dayOfMonth && System.currentTimeMillis() <= timeCheck + 1500) {
                        // 프래그먼트 바꾸기(일간 프래그먼트로)
                    }
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}