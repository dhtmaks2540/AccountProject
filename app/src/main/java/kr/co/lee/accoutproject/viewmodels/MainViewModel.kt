package kr.co.lee.accoutproject.viewmodels

import android.accounts.Account
import android.app.usage.UsageEvents
import android.media.metrics.Event
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.calendar.CalendarUtils
import kr.co.lee.accoutproject.data.*
import kr.co.lee.accoutproject.utilities.ioThread
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

// 인자로 DAO 객체를 받는 ViewModel
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AccountRepository
): ViewModel() {
    private val _date = MutableLiveData<LocalDate>()
    private val _incomeMoney = MutableLiveData<Long>()
    private val _depositMoney = MutableLiveData<Long>()
    private val _dateAccounts = MutableLiveData<TreeMap<LocalDate, ArrayList<AccountAndType>?>>()

    val dateAccounts: LiveData<TreeMap<LocalDate, ArrayList<AccountAndType>?>>
        get() = _dateAccounts

    val incomeMoney: LiveData<Long>
        get() = _incomeMoney

    val depositMoney: LiveData<Long>
        get() = _depositMoney

    val date: LiveData<LocalDate>
        get() = _date

    fun setDate(dateTime: LocalDate) {
        _date.value = dateTime
    }

    fun setAccounts() {
        ioThread {
            val accounts = repository.getAccounts(date.value!!.year, date.value!!.monthOfYear)
            val dateHashMap = TreeMap<LocalDate, ArrayList<AccountAndType>?>()

            for(day in CalendarUtils.getMonthList(date.value!!)) {
                dateHashMap.put(day, null)
            }

            accounts.forEach {
                val key = LocalDate(it.account.year, it.account.month, it.account.day)
                if(dateHashMap[key] == null) {
                    dateHashMap.put(key, ArrayList())
                }
                dateHashMap[key]?.add(it)
            }

            _dateAccounts.postValue(dateHashMap)

            _incomeMoney.postValue(accounts.filter { it.type.typeForm == 1 }.sumOf { it.account.money })
            _depositMoney.postValue(accounts.filter { it.type.typeForm == 0 }.sumOf { it.account.money })
        }
    }
}