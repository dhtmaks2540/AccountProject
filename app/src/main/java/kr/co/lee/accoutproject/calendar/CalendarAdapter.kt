package kr.co.lee.accoutproject.calendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.lee.accoutproject.ui.CalendarFragment
import kr.co.lee.accoutproject.ui.MonthFragment
import org.joda.time.DateTime

// ViewPager2를 위한 어댑터
class CalendarAdapter(fm: FragmentActivity): FragmentStateAdapter(fm) {
    // DateTime 객체로 현재 시간 획득
    // 달의 첫 번째 day(하루의 시작 시간으로) millisecond
    private var start: Long = DateTime().withDayOfMonth(1).withTimeAtStartOfDay().millis

    // 아이템의 개수를 받는 메소드
    override fun getItemCount(): Int = Int.MAX_VALUE

    // 특정한 포지션과 관련된 프래그먼트를 제공하는 메소드
    override fun createFragment(position: Int): Fragment {
        // getItemId에서 넘겨준 millis값 받기
        val millis = getItemId(position)
        // 프래그먼트 생성하면서 인자로 millis값 넘기기
        return CalendarFragment.newInstance(millis)
    }
    
    // 해당 page의 id를 제공
    override fun getItemId(position: Int): Long =
        DateTime(start).plusMonths(position - START_POSITION).millis

    // item이 포함되어 있는지 여부를 확인하는 메소드
    override fun containsItem(itemId: Long): Boolean {
        val date = DateTime(itemId)
        return date.dayOfMonth == 1 && date.millisOfDay == 0
    }

    companion object {
        // 중간지점
        const val START_POSITION = Int.MAX_VALUE / 2
    }
}