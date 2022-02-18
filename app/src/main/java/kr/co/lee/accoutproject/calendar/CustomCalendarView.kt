package kr.co.lee.accoutproject.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.calendar.CalendarAdapter
import org.joda.time.DateTime

class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int,
    val currentDate: DateTime
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var grid: GridView
    private var eventHandler: EventHandler? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_calendar, this)

    }

    private fun assignUiElements() {
        grid = findViewById(R.id.calendar_view)
    }

    private fun assignClickHandlers() {
        grid.setOnItemClickListener { adapterView, view, position, id ->
            if(eventHandler == null)
                return@setOnItemClickListener

            eventHandler?.onDatePress(adapterView.getItemAtPosition(position) as DateTime)
        }
    }

    fun updateCalendar() { }

    fun updateCalendar(events: HashSet<DateTime>, cells: List<DateTime>) {
        val dateTime = currentDate.withDayOfMonth(1)

        grid.adapter = CalendarAdapter(context, cells, events)
    }

    class CalendarAdapter(
        context: Context,
        days: List<DateTime>,
        val eventDays: HashSet<DateTime>): ArrayAdapter<DateTime>(context, R.layout.control_calendar_day, days) {
            private val inflater = LayoutInflater.from(context)

//        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//            val dateTime = getItem(position)
//            val day = dateTime?.dayOfMonth
//            val month = dateTime?.monthOfYear
//            val year = dateTime?.year
//
//            // today
////            val today =
//        }
    }
}

interface EventHandler {
    fun onDatePress(dateTime: DateTime)
}