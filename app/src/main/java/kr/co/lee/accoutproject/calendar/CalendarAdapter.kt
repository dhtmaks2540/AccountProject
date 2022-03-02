package kr.co.lee.accoutproject.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
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
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var redColor = ResourcesCompat.getColor(context.resources, R.color.money_red, null)

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view

        // LocalDate in Position
        val date = getItem(position)
        val day = date?.dayOfMonth
        val month = date?.monthOfYear
        val year = date?.year

        // Today
        val today = LocalDate()

        // Layout init(inflate)
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_date, parent, false)
        }

        // View init
        val tvDate = convertView?.findViewById<TextView>(R.id.tv_date)
        val tvIncome = convertView?.findViewById<TextView>(R.id.tv_income)
        val tvDeposit = convertView?.findViewById<TextView>(R.id.tv_deposit)

        var incomeMoney: Long = 0L
        var depositMoney: Long = 0L

        // 입금, 출금 금액 계산
        if(events != null) {
            events[date]?.forEach {
                if(it.type.typeForm == 1) incomeMoney += it.account.money
                if(it.type.typeForm == 0) depositMoney += it.account.money
            }
        }

        // Color 획득
        val color = CalendarUtils.getDateColor(date?.dayOfWeek!!)

        // Date Text
        tvDate?.let {
            it.setTypeface(null, Typeface.NORMAL)
            it.setTextColor(Color.BLACK)

            it.setTextColor(color)
            
            if(month != firstDayOfMonth.monthOfYear || year != firstDayOfMonth.year) { // 이번달 날짜가 아니라면
                it.alpha = 0.2f
            } else if(day == today.dayOfMonth && month == today.monthOfYear && year == today.year) { // 오늘에 해당하는 날짜라면
                it.setTypeface(null, Typeface.BOLD)
            }

            it.text = date.dayOfMonth.toString()
        }

        // Money Text
        // 이번달 날짜라면
        if(month == firstDayOfMonth.monthOfYear && year == firstDayOfMonth.year) {
            if(incomeMoney != 0L && depositMoney != 0L) {
                tvIncome?.text = decimalFormat.format(incomeMoney)
                tvDeposit?.text = decimalFormat.format(depositMoney)
            } else if(incomeMoney == 0L && depositMoney != 0L) {
                tvIncome?.text = decimalFormat.format(depositMoney)
                tvIncome?.setTextColor(redColor)
            } else if(incomeMoney != 0L && depositMoney == 0L) {
                tvIncome?.text = decimalFormat.format(incomeMoney)
            }
        }

        return convertView!!
    }

}