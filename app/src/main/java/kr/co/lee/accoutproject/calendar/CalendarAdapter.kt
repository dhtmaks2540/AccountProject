package kr.co.lee.accoutproject.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.utilities.decimalFormat
import org.joda.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

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
                    tvIncome.text = decimalFormat.format(incomeMoney)
                    tvIncome.visibility = View.VISIBLE
                } else {
                    tvIncome.visibility = View.INVISIBLE
                }
            }
        }

        // DepositMoney Text
        tvDeposit?.let {
            if(month == firstDayOfMonth.monthOfYear && year == firstDayOfMonth.year && depositMoney != 0L) {
                tvDeposit.text = decimalFormat.format(depositMoney)
            }
        }

        return convertView!!
    }

}