package kr.co.lee.accoutproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(

): ViewModel() {
    private val _date = MutableLiveData<String>()
    private val _doubleMoneyItem = MutableLiveData<Long>()

    val _moneyItem = MutableLiveData<String>("0")

    val doubleMoneyItem: LiveData<Long>
        get() = _doubleMoneyItem

    val moneyItem: LiveData<String>
        get() = _moneyItem

    val date: LiveData<String>
        get() = _date

    fun setDateItem(date: String) {
        _date.value = date
    }

    fun setDoubleItem(money: Long) {
        _doubleMoneyItem.value = money
    }
}