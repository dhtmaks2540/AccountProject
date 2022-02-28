package kr.co.lee.accoutproject.adapters

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.utilities.decimalFormat
import org.joda.time.LocalDate
import java.util.*

@BindingAdapter("date")
fun setDate(view: TextView, map: TreeMap<LocalDate, ArrayList<AccountAndType?>>) {
    val start = map.firstKey().toString("MM월 dd일")
    val end = map.lastKey().toString("MM월 dd일")
    if(start != end) {
        view.text = "$start - $end"
    } else {
        view.text = "$start"
    }
}

@BindingAdapter("money_text")
fun setMoneyDate(view: TextView, map: TreeMap<LocalDate, ArrayList<AccountAndType>?>) {
    var depositMoney: Long = 0
    for((key, arrayList) in map.entries) {
        arrayList?.let {
            depositMoney += arrayList.filter { it.type.typeForm == 0 }.sumOf { it.account.money }
        }
    }

    view.setTextColor(ResourcesCompat.getColor(view.resources, R.color.money_red, null))
    view.text = "${decimalFormat.format(depositMoney)}원"
}
