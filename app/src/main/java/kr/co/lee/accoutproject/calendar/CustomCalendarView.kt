package kr.co.lee.accoutproject.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import kr.co.lee.accoutproject.R
import org.joda.time.LocalDate
import java.util.jar.Attributes

class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int
): ConstraintLayout(context, attrs, defStyle) {
    var dateFormat: String?
    private val currentDate = LocalDate()

    private lateinit var btnPrev: ImageView
    private lateinit var btnNext: ImageView
    private lateinit var txtDate: TextView
    private lateinit var grid: GridView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_calendar, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.CalendarView)

        try {
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat)
            if(dateFormat == null) {
                dateFormat = DATE_FORMAT
            }
        } finally {
            ta.recycle()
        }

        assignUiElements()
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
            currentDate.plusMonths(1)
        }

        // subtract one month and refresh UI
        btnPrev.setOnClickListener {
            currentDate.minusMonths(1)
        }
    }

    fun updateCalendar(events: HashSet<LocalDate>) {
        val cells = ArrayList<LocalDate>()

    }

    companion object {
        private const val DAYS_COUNT = 42
        private const val DATE_FORMAT = "YYYY MM"
    }

    interface EventHandler {
        fun onDayLongPress(localDate: LocalDate)
    }
}