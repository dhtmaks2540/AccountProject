package kr.co.lee.accoutproject.view.detailactivity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

class DetailViewModel: ViewModel() {
    private val _money = MutableLiveData<String>()

    val money: LiveData<String>
        get() = _money

    fun selectMoney(moneyItem: String) {
        _money.value = moneyItem + "원"
    }
}