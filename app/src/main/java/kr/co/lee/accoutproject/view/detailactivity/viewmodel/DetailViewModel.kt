package kr.co.lee.accoutproject.view.detailactivity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.lee.accoutproject.room.AccountDAO
import kr.co.lee.accoutproject.room.TypeEntity
import kr.co.lee.accoutproject.utility.ioThread
import java.text.DecimalFormat

class DetailViewModel(val accountDao: AccountDAO): ViewModel() {
    private val _money = MutableLiveData<String>()
    private val _types = MutableLiveData<List<TypeEntity>>()

    val types: LiveData<List<TypeEntity>>
        get() = _types

    val money: LiveData<String>
        get() = _money

    fun selectMoney(moneyItem: String) {
        _money.value = moneyItem + "원"
    }

    fun selectTypes(type: Int) {
        ioThread {
            _types.postValue(accountDao.getTypes(typeForm = type))
        }
    }
}