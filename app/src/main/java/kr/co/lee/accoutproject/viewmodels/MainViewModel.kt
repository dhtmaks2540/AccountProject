package kr.co.lee.accoutproject.viewmodels

import android.accounts.Account
import android.app.usage.UsageEvents
import android.media.metrics.Event
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.data.*
import kr.co.lee.accoutproject.utilities.ioThread
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

// 인자로 DAO 객체를 받는 ViewModel
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AccountRepository
): ViewModel() {
    private val _date = MutableLiveData<DateTime>()
    private val _accounts = MutableLiveData<List<AccountAndType>>()
    private val _incomeMoney = MutableLiveData<Long>()
    private val _depositMoney = MutableLiveData<Long>()

    val incomeMoney: LiveData<Long>
        get() = _incomeMoney

    val depositMoney: LiveData<Long>
        get() = _depositMoney

    val date: LiveData<DateTime>
        get() = _date

    val accounts: LiveData<List<AccountAndType>>
        get() = _accounts

    fun setDate(dateTime: DateTime) {
        _date.value = dateTime
    }

    fun setAccounts() {
        ioThread {
            _accounts.postValue(repository.getAccounts(date.value!!.year, date.value!!.monthOfYear))
        }
    }

    fun setMoney() {
        _incomeMoney.postValue(accounts.value?.filter { it.type.typeForm == 1 }?.sumOf { it.account.money })
        _depositMoney.postValue(accounts.value?.filter { it.type.typeForm == 0 }?.sumOf { it.account.money })
    }
}