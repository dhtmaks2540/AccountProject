package kr.co.lee.accoutproject.calendar

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate

// 유틸 클래스
class CalendarUtils {
    companion object {
        // 6주 셋팅
        const val WEEKS_PER_MONTH = 6

        /**
         * 선택된 날짜에 해당하는 월간 달력을 반환한다.
         */
        fun getMonthList(dateTime: LocalDate): List<LocalDate> {
            val list = mutableListOf<LocalDate>()

            // 달의 1일
            val date = dateTime.withDayOfMonth(1)
            // 이전 달의 일 개수
            val prev = getPrevOffSet(date)

            // 달의 시작 값
            val startValue = date.minusDays(prev)

            // 7 * 6(42를 한 달로)
            val totalDay = DateTimeConstants.DAYS_PER_WEEK * WEEKS_PER_MONTH

            for (i in 0 until totalDay) {
                list.add(LocalDate(startValue.plusDays(i)))
            }

            return list
        }

        /**
         * 해당 calendar 의 이전 달의 일 갯수를 반환한다.
         */
        private fun getPrevOffSet(dateTime: LocalDate): Int {
            // 달의 첫 번째 날의 주
            var prevMonthTailOffset = dateTime.dayOfWeek

            // 7(한 주)로 나눈 나머지
            if (prevMonthTailOffset >= 7) prevMonthTailOffset %= 7

            return prevMonthTailOffset
        }

        /**
         * 같은 달인지 체크
         */
        fun isSameMonth(first: DateTime, second: DateTime): Boolean =
            first.year == second.year && first.monthOfYear == second.monthOfYear

        /**
         * 해당 요일의 색깔을 반환한다.
         * 일요일 -> 빨간색
         * 토요일 -> 파란색
         * 나머지 -> 검정색
         */
        @ColorInt
        fun getDateColor(@IntRange(from=1, to=7) dayOfWeek: Int): Int {
            return when (dayOfWeek) {
                /* 토요일은 파란색 */
                DateTimeConstants.SATURDAY -> Color.parseColor("#2962FF")
                /* 일요일 빨간색 */
                DateTimeConstants.SUNDAY -> Color.parseColor("#D32F2F")
                /* 그 외 검정색 */
                else -> Color.parseColor("#000000")
            }
        }
    }
}