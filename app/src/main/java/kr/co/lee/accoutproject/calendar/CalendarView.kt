package kr.co.lee.accoutproject.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.calendar.CalendarUtils.Companion.WEEKS_PER_MONTH
import kr.co.lee.accoutproject.data.AccountAndType
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import kotlin.math.max

// ViewGroup은 다른 뷰들을 포함할 수 있는 뷰(LinearLayout이나 GridLayout이 이 클래스를 상속받고있음)
// ViewGroup를 상속받아 작성하여 여러 뷰를 합쳐서 하나의 뷰로 표현하기 위한 커스텀뷰(레이아웃 역할)
class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.calendarViewStyle,
    @StyleRes defStyleRes: Int = R.style.Calendar_CalendarViewStyle
// ContextThemeWrapper : Context의 테마를 수정하거나 바꿀 수 있는 Context Wrapper
) : ViewGroup(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {
    private var _height: Float = 0f

    // 초기화
    init {
        // TypedArray 획득(Attributes)
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            _height = getDimension(R.styleable.CalendarView_dayHeight, 0f)
        }
    }

    /**
     * Measure
     */
    // 크기를 결정하는 메소드
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // getSuggestedMinimumHeight : 뷰가 사용할 수 있수 있는 크기의 최솟값
        val h = paddingTop + paddingBottom + max(suggestedMinimumHeight, (_height * WEEKS_PER_MONTH).toInt())
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec), h)
    }

    /**
     * Layout
     */
    // 레이아웃 안에 포지션 정하기
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 넓이
        val iWidth = (width / DateTimeConstants.DAYS_PER_WEEK).toFloat()
        // 높이
        val iHeight = (height / WEEKS_PER_MONTH).toFloat()

        var index = 0
        children.forEach { view ->
            val left = (index % DateTimeConstants.DAYS_PER_WEEK) * iWidth
            val top = (index / DateTimeConstants.DAYS_PER_WEEK) * iHeight

            // 왼쪽, 위, 오른쪽, 아래
            view.layout(left.toInt(), top.toInt(), (left + iWidth).toInt(), (top + iHeight).toInt())

            index++
        }
    }

    /**
     * 달력 그리기 시작한다.
     * @param firstDayOfMonth   한 달의 시작 요일
     * @param list              달력이 가지고 있는 요일과 이벤트 목록 (총 42개)
     */
    fun initCalendar(firstDayOfMonth: DateTime, list: List<DateTime>) {
//        for(i in list.indices) {
//            addView(DayItemView(
//                context = context,
//                date = list[i],
//                firstDayOfMonth = firstDayOfMonth]
//            )
//        }
        list.forEach {
            // ChildView를 추가
            addView(DayItemView(
                context = context,
                date = it,
                firstDayOfMonth = firstDayOfMonth,
            ))
        }
    }
}