package kr.co.lee.accoutproject.viewmodels

import android.app.usage.UsageEvents
import android.media.metrics.Event
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.data.AccountDAO
import kr.co.lee.accoutproject.data.AccountEntity
import kr.co.lee.accoutproject.utilities.ioThread
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

// 인자로 DAO 객체를 받는 ViewModel
class MainViewModel(
    private val accountDao: AccountDAO): ViewModel() {
    // DateTime 타입의 MutableLiveData
    private val _selectedDate = MutableLiveData<DateTime>()
    private val _accounts = MutableLiveData<List<AccountEntity>>()

    val selectedDate: LiveData<DateTime>
        get() = _selectedDate

    val accounts: LiveData<List<AccountEntity>>
        get() = _accounts

    fun setDate(dateTime: DateTime) {
        _selectedDate.value = dateTime
    }

    fun setAccounts() {
//        _accounts.value = accountDao.getMonthAccount(2022, 2)
        ioThread {
            _accounts.value = accountDao.getMonthAccount()
        }
    }
}