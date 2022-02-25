package kr.co.lee.accoutproject.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.get
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.databinding.CustomCalendarBinding
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.textStyle,
    @StyleRes defStyleRes: Int = R.style.CustomCalendarView_textStyle
): ConstraintLayout(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {
    // current displayed month
    private var currentDate = LocalDate()
    private var prevSelectedPosition = 0
    private lateinit var color: Color

    // event handling
    private var eventHandler: EventHandler? = null
    private val binding: CustomCalendarBinding =
        CustomCalendarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        // TypedArray 획득(Attributes)
//        context.withStyledAttributes(
//            attrs,
//            R.styleable.CustomCalendarView,
//            defStyleAttr,
//            defStyleRes
//        ) {
//            dateFormat = getString(R.styleable.CustomCalendarView_textFormat)
//        }

        binding.apply {
            // GridView Click Listener
            val defaultColor = ResourcesCompat.getColor(resources, R.color.default_color, null)
            val clickColor = ResourcesCompat.getColor(resources, R.color.click_color, null)
            calendarGrid.setOnItemClickListener { adapterView, view, position, id ->
                // 해당 position item 넘기기
                eventHandler?.onDayPress(adapterView.getItemAtPosition(position) as LocalDate)

                // Set Click Color
                adapterView[prevSelectedPosition].setBackgroundColor(defaultColor)
                view.setBackgroundColor(clickColor)

                prevSelectedPosition = position
            }
        }
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

    // 
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

    // ArrayAdapter
    class CalendarAdapter constructor(
        context: Context,
        days: ArrayList<LocalDate>,
        private val events: TreeMap<LocalDate, ArrayList<AccountAndType>?>?,
        private val firstDayOfMonth: LocalDate
    ): ArrayAdapter<LocalDate>(context, 0, days) {
        // for view inflation
        private var inflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
                var convertView = view

            // LocalDate in Position
            val date = getItem(position)
            val day = date?.dayOfMonth
            val month = date?.monthOfYear
            val year = date?.year

            // Today
            val today = LocalDate()

            // Layout Inflate
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.item_calendar, parent, false)
            }

            // View 초기화
            val tvDate = convertView?.findViewById<TextView>(R.id.tv_date)
            val tvIncome = convertView?.findViewById<TextView>(R.id.tv_income)
            val tvDeposit = convertView?.findViewById<TextView>(R.id.tv_deposit)

            var incomeMoney: Long = 0L
            var depositMoney: Long = 0L

            // if this day has an event, specify event image
            if(events != null) {
                events[date]?.forEach {
                    if(it.type.typeForm == 1) incomeMoney += it.account.money
                    if(it.type.typeForm == 0) depositMoney += it.account.money
                }
            }

            val color = CalendarUtils.getDateColor(date?.dayOfWeek!!)

            // Date Text
            tvDate?.let {
                it.setTypeface(null, Typeface.NORMAL)
                it.setTextColor(Color.BLACK)

                it.setTextColor(color)

                if(month != firstDayOfMonth.monthOfYear || year != firstDayOfMonth.year) {
                    // if this day is outside current month, grey it out
                    it.alpha = 0.2f
                } else if(day == today.dayOfMonth && month == today.monthOfYear && year == today.year) {
                    // if it is today, set it to blue/bold
                    it.setTypeface(null, Typeface.BOLD)
                }

                it.text = date.dayOfMonth.toString()
            }

            // IncomeMoney Text
            tvIncome?.let {
                if(month == firstDayOfMonth.monthOfYear && year == firstDayOfMonth.year) {
                    if(incomeMoney != 0L) {
                        tvIncome.text = incomeMoney.toString()
                        tvIncome.visibility = View.VISIBLE
                    } else {
                        tvIncome.visibility = View.INVISIBLE
                    }
                }
            }

            // DepositMoney Text
            tvDeposit?.let {
                if(month == firstDayOfMonth.monthOfYear && year == firstDayOfMonth.year && depositMoney != 0L) {
                    tvDeposit.text = depositMoney.toString()
                }
            }

            return convertView!!
        }

    }

    // EventHandler Interface
    interface EventHandler {
        fun onDayPress(localDate: LocalDate)
    }

    // Defalut Format
//    companion object {
//        private const val DATE_FORMAT = "YYYY MM"
//    }

}