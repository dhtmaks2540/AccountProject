package kr.co.lee.accoutproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.lee.accoutproject.data.AccountDAO
import kr.co.lee.accoutproject.data.TypeEntity
import kr.co.lee.accoutproject.utilities.ioThread

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