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
import kr.co.lee.accoutproject.R
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import java.util.jar.Attributes

class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.textStyle,
    @StyleRes defStyleRes: Int = R.style.CustomCalendarView_textStyle
): ConstraintLayout(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {
    var dateFormat: String? = DATE_FORMAT

    // current displayed month
    private var currentDate = LocalDate()

    // internal components
    private lateinit var btnPrev: ImageView
    private lateinit var btnNext: ImageView
    private lateinit var txtDate: TextView
    private lateinit var grid: GridView

    // event handling
    private var eventHandler: EventHandler? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_calendar, this)

        // TypedArray 획득(Attributes)
        context.withStyledAttributes(attrs, R.styleable.CustomCalendarView, defStyleAttr, defStyleRes) {
            dateFormat = getString(R.styleable.CustomCalendarView_textFormat)
        }

        assignUiElements()
        assignClickHandlers()
        updateCalendar(currentDate)
    }

    private fun assignUiElements() {
        // layout is inflated, assign local variables to components
        btnPrev = findViewById(R.id.left_btn)
        btnNext = findViewById(R.id.right_btn)
        txtDate = findViewById(R.id.date_view)
        grid = findViewById(R.id.calendar_grid)
    }

    private fun assignClickHandlers() {
        // add one month and refresh UI
        btnNext.setOnClickListener {
            val nextMonth = currentDate.plusMonths(1)
            currentDate = nextMonth
            updateCalendar(nextMonth)
        }

        // subtract one month and refresh UI
        btnPrev.setOnClickListener {
            val prevMonth = currentDate.minusMonths(1)
            currentDate = prevMonth
            updateCalendar(prevMonth)
        }
    }

    private fun updateCalendar(date: LocalDate, events: HashSet<LocalDate>? = null) {
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
        grid.adapter = CalendarAdapter(context, cells, events, calendar)

        // update title
        txtDate.text = currentDate.toString(dateFormat)
    }

    class CalendarAdapter constructor(
        context: Context,
        days: ArrayList<LocalDate>,
        val eventDays: HashSet<LocalDate>?,
        val firstDayOfMonth: LocalDate
    ): ArrayAdapter<LocalDate>(context, 0, days) {
        // for view inflation
        private var inflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
                var convertView = view
            // day in question
            val date = getItem(position)
            val day = date?.dayOfMonth
            val month = date?.monthOfYear
            val year = date?.year

            // today
            val today = LocalDate()

            if(convertView == null) {
                convertView = inflater.inflate(R.layout.item_calendar, parent, false)
            }

            // if this day has an event, specify event image
            eventDays?.let {
                it.forEach { date ->
                    if(date.dayOfMonth == day &&
                            date.monthOfYear == month &&
                            date.year == year) {

                    }
                }
            }

            val tvDate = convertView?.findViewById<TextView>(R.id.tv_date)
            val tvIncome = convertView?.findViewById<TextView>(R.id.tv_income)
            val tvDeposit = convertView?.findViewById<TextView>(R.id.tv_deposit)

            val color = CalendarUtils.getDateColor(date?.dayOfWeek!!)

            tvDate?.let {
                it.setTypeface(null, Typeface.NORMAL)
                it.setTextColor(Color.BLACK)

                it.setTextColor(color)

                if(month != firstDayOfMonth.monthOfYear || year != firstDayOfMonth.year) {
                    // if this day is outside current month, grey it out
//                    it.setTextColor(ResourcesCompat.getColor(context.resources, R.color.greyed_out, null))
                    it.alpha = 0.2f
                } else if(day == today.dayOfMonth && month == today.monthOfYear && year == today.year) {
                    // if it is today, set it to blue/bold
                    it.setTypeface(null, Typeface.BOLD)
//                    it.setTextColor(ResourcesCompat.getColor(context.resources, R.color.today, null))
                }

                it.text = date.dayOfMonth.toString()
            }

            return convertView!!
        }
    }

    companion object {
        private const val DATE_FORMAT = "YYYY MM"
    }

    interface EventHandler {
        fun onDayLongPress(localDate: LocalDate)
    }
}