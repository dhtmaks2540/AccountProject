package kr.co.lee.accoutproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

class AddViewModel: ViewModel() {
    private val decimalFormat = DecimalFormat("#,###")

    private val _date = MutableLiveData<String>()
    private val _money = MutableLiveData<String>("0")

    val _test = MutableLiveData("0")

    val mediator = MediatorLiveData<String>().apply {
        addSource(_test) { value ->
            val money = value.toString().replace(",","").toDouble()
            val result = decimalFormat.format(money)
            setValue(result)
        }
    }

    val date: LiveData<String>
        get() = _date

    val money: LiveData<String>
        get() = _money

    fun setDateItem(dateItem: String) {
        _date.value = dateItem
    }

    fun setMoneyItem(moneyItem: String) {
        _money.value = moneyItem
    }
}