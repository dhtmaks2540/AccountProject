package kr.co.lee.accoutproject.view.monthfragment.view

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.calendar.CalendarAdapter
import kr.co.lee.accoutproject.databinding.FragmentMonthBinding
import kr.co.lee.accoutproject.view.main.viewmodel.MainViewModel
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

class MonthFragment : Fragment() {
    // DataBinding 코드
    private var _binding: FragmentMonthBinding? = null
    private val binding: FragmentMonthBinding
        get() = _binding!!

    private var timeCheck: Long = 0L
    private var prev_year = 0
    private var prev_month = 0
    private var prev_day = 0

    // FragmentStateAdapter
    private lateinit var calendarAdapter: CalendarAdapter
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // DataBinding 레이아웃 초기화
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_month, container, false)

//        // FragmentStateAdapter 초기화
//        activity?.let {
//            calendarAdapter = CalendarAdapter(activity!!)
//            binding.calendar.adapter = calendarAdapter
//            // 초기 페이지 지정
//            binding.calendar.setCurrentItem(CalendarAdapter.START_POSITION, false)
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 날짜 얻어오는 방법
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val date = Date(binding.calendar.date)
        Toast.makeText(activity, dateFormat.format(date), Toast.LENGTH_SHORT).show()

        // 선택 리스너
        binding.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            viewModel.selectItem(DateTime().withYear(year).withMonthOfYear(month + 1).withDayOfMonth(dayOfMonth))
            if (prev_year != year || prev_month != month || prev_day != dayOfMonth || System.currentTimeMillis() > timeCheck + 1500) {
                timeCheck = System.currentTimeMillis()
                prev_year = year
                prev_month = month
                prev_day = dayOfMonth
            } else if(prev_year == year && prev_month == month && prev_day == dayOfMonth && System.currentTimeMillis() <= timeCheck + 1500) {
                // 프래그먼트 바꾸기
                Toast.makeText(activity, "1.5초 이내 클릭", Toast.LENGTH_SHORT).show()
            }
        }
    }
}