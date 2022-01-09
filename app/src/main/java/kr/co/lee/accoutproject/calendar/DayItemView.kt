package kr.co.lee.accoutproject.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.calendar.CalendarUtils.Companion.getDateColor
import kr.co.lee.accoutproject.calendar.CalendarUtils.Companion.isSameMonth
import org.joda.time.DateTime

// View를 상속받는 커스텀 뷰
// 날자 하나를 그리는 역할을 수행
class DayItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes private val defStyleAttr: Int = R.attr.itemViewStyle,
    @StyleRes private val defStyleRes: Int = R.style.Calendar_ItemViewStyle,
    private val date: DateTime = DateTime(),
    private val firstDayOfMonth: DateTime = DateTime()
// // ContextThemeWrapper : Context의 테마를 수정하거나 바꿀 수 있는 Context Wrapper
) : View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {

    private val bounds = Rect()

    private var paint: Paint = Paint()

    init {
        /* Attributes */
        // TypedArray receiver에서 블록을 실행(TypedArray는 커스텀 특성에 나열된 특성 값을 집합에 저장)
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            val dayTextSize = getDimensionPixelSize(R.styleable.CalendarView_dayTextSize, 0).toFloat()

            /* 흰색 배경에 유색 글씨 */
            paint = TextPaint().apply {
                isAntiAlias = true
                textSize = dayTextSize
                color = getDateColor(date.dayOfWeek)
                if (!isSameMonth(date, firstDayOfMonth)) {
                    alpha = 50
                }
            }
        }
    }

    // Draw는 무멋을 그릴지를 정하는 클래스
    // Paint는 어떻게 그릴지를 정하는 클래스
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        // 날짜 획득
        val date = date.dayOfMonth.toString()
        // 텍스트를 둘러싸고 있는 가장 작은 사각형을 반환
        paint.getTextBounds(date, 0, date.length, bounds)
        canvas.drawText(
            date,
            (width / 2 - bounds.width() / 2).toFloat() - 2,
            (height / 2 + bounds.height() / 2).toFloat(),
            paint
        )
    }
}