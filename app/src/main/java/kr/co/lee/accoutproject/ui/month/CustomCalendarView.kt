package kr.co.lee.accoutproject.ui.month

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.databinding.CustomCalendarBinding
import kr.co.lee.accoutproject.model.AccountAndType
import kr.co.lee.accoutproject.ui.adapter.CalendarAdapter
import kr.co.lee.accoutproject.utilities.CalendarUtils
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import java.util.*

class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.textStyle,
    @StyleRes defStyleRes: Int = R.style.CustomCalendarView_textStyle
): ConstraintLayout(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {
    // current displayed month
    private var currentDate = LocalDate()
    private var prevSelectedPosition = 0

    // event handling
    private var eventHandler: EventHandler? = null
    private val binding: CustomCalendarBinding =
        CustomCalendarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.apply {
            // GridView Click Listener
            val defaultColor = ResourcesCompat.getColor(resources, R.color.default_color, null)
            val clickColor = ResourcesCompat.getColor(resources, R.color.click_color, null)
            calendarGrid.setOnItemClickListener { adapterView, view, position, id ->
                val date = adapterView.getItemAtPosition(position) as LocalDate
                // 해당 position item 넘기기
                eventHandler?.onDayPress(date)

//                setSelectedColor(adapterView, view, prevSelectedPosition, defaultColor, clickColor)

                prevSelectedPosition = position
            }
        }
    }

    // Set Click Color
    private fun setSelectedColor(adapterView: AdapterView<*>, view: View, position: Int, adapterColor: Int, viewColor: Int) {
        adapterView[position].setBackgroundColor(adapterColor)
        view.setBackgroundColor(viewColor)
    }

    // 이전달 버튼
    fun prevButtonClick(): LocalDate {
        val prevMonth = currentDate.minusMonths(1)
        currentDate = prevMonth

        return currentDate
    }

    // 다음달 버튼
    fun nextButtonClick(): LocalDate {
        val nextMonth = currentDate.plusMonths(1)
        currentDate = nextMonth

        return currentDate
    }

    // 첫 달
    fun setFirstDate(): LocalDate {
        return currentDate
    }

    // EventHandler 설정
    fun setEventHandler(eventHandler: EventHandler) {
        this.eventHandler = eventHandler
    }

    // 달력 update
    fun updateCalendar(events: TreeMap<LocalDate, ArrayList<AccountAndType>?>? = null) {
        updateCalendar(currentDate, events)
    }

    // 달력 update
    private fun updateCalendar(date: LocalDate, events: TreeMap<LocalDate, ArrayList<AccountAndType>?>? = null) {
        val cells = ArrayList<LocalDate>()
        val calendar = date.withDayOfMonth(1)

        // determine the cell for current month's beginning
        var monthBeggingCell = calendar.dayOfWeek

        if(monthBeggingCell >= 7) monthBeggingCell %= 7

        // move calendar backwards to the begging of the week
        val date = calendar.minusDays(monthBeggingCell)

        val totalDay = DateTimeConstants.DAYS_PER_WEEK * CalendarUtils.WEEKS_PER_MONTH

        for(i in 0 until totalDay) {
            cells.add(LocalDate(date.plusDays(i)))
        }

        // update grid
        binding.calendarGrid.adapter = CalendarAdapter(context, cells, events, calendar)
    }

    // EventHandler Interface
    interface EventHandler {
        fun onDayPress(localDate: LocalDate)
    }
}