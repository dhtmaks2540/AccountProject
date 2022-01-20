package kr.co.lee.accoutproject.view.addactivity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddViewModel: ViewModel() {
    private val _date = MutableLiveData<String>()
    private val _money = MutableLiveData<String>("0")

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