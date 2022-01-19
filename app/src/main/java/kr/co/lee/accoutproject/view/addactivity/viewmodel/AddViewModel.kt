package kr.co.lee.accoutproject.view.addactivity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddViewModel: ViewModel() {
    private val _date = MutableLiveData<String>()
    private val _money = MutableLiveData<Double>()

    val date: LiveData<String>
        get() = _date

    val money: LiveData<Double>
        get() = _money

    fun setDateItem(dateItem: String) {
        _date.value = dateItem
    }

    fun setMoneyItem(moneyItem: Double) {
        _money.value = moneyItem
    }
}