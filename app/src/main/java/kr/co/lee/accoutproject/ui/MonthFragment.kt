package kr.co.lee.accoutproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.calendar.CalendarAdapter
import kr.co.lee.accoutproject.databinding.FragmentMonthBinding

class MonthFragment : Fragment() {
    // DataBinding 코드
    private var _binding: FragmentMonthBinding? = null
    private val binding: FragmentMonthBinding
        get() = _binding!!

    // FragmentStateAdapter
    private lateinit var calendarAdapter: CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // DataBinding 레이아웃 초기화
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_month, container, false)

        // FragmentStateAdapter 초기화
        activity?.let {
            calendarAdapter = CalendarAdapter(activity!!)
            binding.calendar.adapter = calendarAdapter
            // 초기 페이지 지정
            binding.calendar.setCurrentItem(CalendarAdapter.START_POSITION, false)
        }

        return binding.root
    }
}