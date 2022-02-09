package kr.co.lee.accoutproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.lee.accoutproject.data.*
import kr.co.lee.accoutproject.utilities.ioThread
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val typeRepository: TypeRepository,
    private val accountRepository: AccountRepository): ViewModel()
{
    private val _moneyItem = MutableLiveData<String>()
    private val _typesItem = MutableLiveData<List<TypeEntity>>()
    private val _dateItem = MutableLiveData<String>()
    private val _doubleMoney = MutableLiveData<Long>()

    val _contentItem = MutableLiveData<String>("")

    val doubleMoney: LiveData<Long>
        get() = _doubleMoney

    val content: LiveData<String>
        get() = _contentItem

    val types: LiveData<List<TypeEntity>>
        get() = _typesItem

    val money: LiveData<String>
        get() = _moneyItem

    val date: LiveData<String>
        get() = _dateItem

    fun setMoney(money: String) {
        _moneyItem.value = money + "원"
    }

    fun setDate(date: String) {
        _dateItem.value = date
    }

    fun setTypes(type: Int) {
        ioThread {
            _typesItem.postValue(typeRepository.getTypes(typeForm = type))
        }
    }

    fun setAccount(money: Long, content: String, date: String, typeId: Int) {
        ioThread {
            val dateList = date.split("/").map { it.toInt() }
            val accountEntity = AccountEntity(0, year = dateList[0], month = dateList[1], day = dateList[2], content = content, money = money, typeId = typeId)
            accountRepository.insertAccount(accountEntity)
        }
    }

    fun setDoubleMoney(money: Long) {
        _doubleMoney.value = money
    }
}