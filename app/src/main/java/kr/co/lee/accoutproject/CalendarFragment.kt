package kr.co.lee.accoutproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.calendar.CalendarUtils.Companion.getMonthList
import kr.co.lee.accoutproject.calendar.CalendarView
import kr.co.lee.accoutproject.databinding.FragmentCalendarBinding
import kr.co.lee.accoutproject.viewmodels.MainViewModel
import org.joda.time.DateTime

@AndroidEntryPoint
class CalendarFragment : Fragment() {
    // DataBinding 코드
    private var _binding: FragmentCalendarBinding? = null
    private val binding: FragmentCalendarBinding
        get() = _binding!!

    private var millis: Long = 0L
    lateinit var calendarView: CalendarView

    // Fragment KTX를 사용하여 Activity ViewModel 초기화
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // arguments가 null이 아니라면. 즉, millis값이 정상적으로 넘어왔다면
        arguments?.let {
            millis = it.getLong(MILLIS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // DataBinding 코드
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)

        binding.apply {
            // calendarView 초기화
            calendarView.initCalendar(DateTime(millis), getMonthList(DateTime(millis)))
        }
        return binding.root
    }

    companion object {
        private const val MILLIS = "MILLIS"

        // CalendarFragment 만드는 메소드
        // 넘어온 millis값 넣은 인자 생성 인자 셋팅
        fun newInstance(millis: Long) = CalendarFragment().apply {
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