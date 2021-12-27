package kr.co.lee.accoutproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.calendar.CalendarAdapter
import kr.co.lee.accoutproject.calendar.CalendarUtils.Companion.getMonthList
import kr.co.lee.accoutproject.databinding.MonthFragmentBinding
import org.joda.time.DateTime

class MonthFragment : Fragment() {
    private var _binding: MonthFragmentBinding? = null
    private val binding: MonthFragmentBinding
        get() = _binding!!

    private lateinit var calendarAdapter: CalendarAdapter
    private var millis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            millis = it.getLong(MILLIS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.month_fragment, container, false)

        binding.calendarView.initCalendar(DateTime(millis), getMonthList(DateTime(millis)))

        activity?.let {
            calendarAdapter = CalendarAdapter(activity!!)
            binding.calendar.adapter = calendarAdapter
            binding.calendar.setCurrentItem(CalendarAdapter.START_POSITION, false)
        }

        return binding.root
    }

    companion object {
        private const val MILLIS = "MILLIS"

        fun newInstance(millis: Long) = MonthFragment().apply {
            arguments = Bundle().apply {
                putLong(MILLIS, millis)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}