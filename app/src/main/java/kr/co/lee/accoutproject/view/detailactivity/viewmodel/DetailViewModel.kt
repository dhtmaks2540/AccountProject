package kr.co.lee.accoutproject.view.detailactivity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

class DetailViewModel: ViewModel() {
    private val _money = MutableLiveData<Double>()

    val money: LiveData<Double>
        get() = _money

    fun selectMoney(moneyItem: Double) {
        _money.value = moneyItem
    }
}