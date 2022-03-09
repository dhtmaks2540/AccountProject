package kr.co.lee.accoutproject.ui.main

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.utilities.CalendarUtils
import kr.co.lee.accoutproject.model.AccountAndType
import kr.co.lee.accoutproject.repository.AccountRepository
import kr.co.lee.accoutproject.utilities.PageType
import kr.co.lee.accoutproject.utilities.ioThread
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import java.util.*
import javax.inject.Inject
import kotlin.collections.set

// 인자로 DAO 객체를 받는 ViewModel
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AccountRepository
): ViewModel() {
    private val _date = MutableLiveData<LocalDate>()
    private val _incomeMoney = MutableLiveData<Long>()
    private val _depositMoney = MutableLiveData<Long>()
    private val _dateAccounts = MutableLiveData<TreeMap<LocalDate, ArrayList<AccountAndType>?>>()
    private val _accounts = MutableLiveData<List<AccountAndType>>()
    private val _weekAccounts = MutableLiveData<Array<TreeMap<LocalDate, ArrayList<AccountAndType>?>>>()
    private val _currentPageType = MutableLiveData(PageType.PAGE1)

    val currentPageType: LiveData<PageType>
        get() = _currentPageType

    val weekAccounts: LiveData<Array<TreeMap<LocalDate, ArrayList<AccountAndType>?>>>
        get() = _weekAccounts

    val accounts: LiveData<List<AccountAndType>>
        get() = _accounts

    val dateAccounts: LiveData<TreeMap<LocalDate, ArrayList<AccountAndType>?>>
        get() = _dateAccounts

    val incomeMoney: LiveData<Long>
        get() = _incomeMoney

    val depositMoney: LiveData<Long>
        get() = _depositMoney

    val date: LiveData<LocalDate>
        get() = _date

    // Date 지정
    fun setDate(dateTime: LocalDate) {
        _date.value = dateTime
    }

    // Account 정보 불러오기
    fun setAccount() {
        ioThread {
            _accounts.postValue(repository.getAccounts(date.value!!.year, date.value!!.monthOfYear))
        }
    }

    // 달 단위 Account 정보 불러오기
    fun setDateAccounts() {
        val dateHashMap = TreeMap<LocalDate, ArrayList<AccountAndType>?>()

        for(day in CalendarUtils.getMonthList(date.value!!)) {
            dateHashMap[day] = null
        }

        accounts.value?.forEach {
            val key = LocalDate(it.account.year, it.account.month, it.account.day)
            if(dateHashMap[key] == null) {
                dateHashMap[key] = ArrayList()
            }
            dateHashMap[key]?.add(it)
        }

        _dateAccounts.postValue(dateHashMap)

        _incomeMoney.postValue(accounts.value?.filter { it.type.typeForm == 1 }?.sumOf { it.account.money })
        _depositMoney.postValue(accounts.value?.filter { it.type.typeForm == 0 }?.sumOf { it.account.money })
    }

    // 주 단위 Account 정보 불러오기
    fun setWeeksAccounts() {
        val firstDay = date.value?.dayOfMonth()?.withMinimumValue()!!
        val lastDay = date.value?.dayOfMonth()?.withMaximumValue()!!

        val arrayList = ArrayList<TreeMap<LocalDate, ArrayList<AccountAndType>?>>()

        var size = 1

        for(i in 0..(lastDay.dayOfMonth - firstDay.dayOfMonth)) {
            val dateMap = TreeMap<LocalDate, ArrayList<AccountAndType>?>()
            val key = firstDay.plusDays(i)!!
            if(i != (lastDay.dayOfMonth - firstDay.dayOfMonth) && key.dayOfWeek == DateTimeConstants.SATURDAY) size += 1
            dateMap[key] = null
            arrayList.add(dateMap)
        }

        accounts.value?.forEach {
            val index = it.account.day - firstDay.dayOfMonth
            val date = LocalDate(it.account.year, it.account.month, it.account.day)
            if(arrayList[index][date] == null) {
                arrayList[index][date] = ArrayList()
            }

            arrayList[index][date]?.add(it)
        }

        val weekAccounts = Array<TreeMap<LocalDate, ArrayList<AccountAndType>?>>(size) { TreeMap() }

        var index = 0

        for((i, value) in arrayList.withIndex()) {
            val key = firstDay.plusDays(i)
            weekAccounts[index][key] = value[key]
            if(key.dayOfWeek == DateTimeConstants.SATURDAY) index++
        }

        _weekAccounts.postValue(weekAccounts)
    }

    // MenuItem에 따른 PageType 변경
    fun setCurrentPage(menuItem: MenuItem): Boolean {
        val menuItemId = menuItem.itemId
        val pageType = getPageType(menuItemId)
        changeCurrentPage(pageType)

        return true
    }

    // PageType 획득
    private fun getPageType(menuItemId: Int): PageType {
        return when(menuItemId) {
            R.id.action_month -> PageType.PAGE1
            R.id.action_week -> PageType.PAGE2
            else -> throw IllegalArgumentException("Not found menu item id")
        }
    }

    // 현재 LiveData에 저장된 PageType과 비교하여 다르면 변경
    private fun changeCurrentPage(pageType: PageType) {
        if(currentPageType.value == pageType) return

        _currentPageType.value = pageType
    }
}