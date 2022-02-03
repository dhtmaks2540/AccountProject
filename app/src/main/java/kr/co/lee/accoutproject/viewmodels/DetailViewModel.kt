package kr.co.lee.accoutproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.lee.accoutproject.data.AccountDAO
import kr.co.lee.accoutproject.data.TypeDAO
import kr.co.lee.accoutproject.data.TypeEntity
import kr.co.lee.accoutproject.utilities.ioThread
import javax.inject.Inject

class DetailViewModel (
    private val typeDAO: TypeDAO): ViewModel()
{
    private val _money = MutableLiveData<String>()
    private val _types = MutableLiveData<List<TypeEntity>>()
    private val _date = MutableLiveData<String>()

    val types: LiveData<List<TypeEntity>>
        get() = _types

    val money: LiveData<String>
        get() = _money

    val date: LiveData<String>
        get() = _date

    fun selectMoney(moneyItem: String) {
        _money.value = moneyItem + "원"
    }

    fun selectDate(dateItem: String) {
        _date.value = dateItem
    }

    fun selectTypes(type: Int) {
        ioThread {
            _types.postValue(typeDAO.getTypes(typeForm = type))
        }
    }
}