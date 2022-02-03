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
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

// 인자로 DAO 객체를 받는 ViewModel
class MainViewModel(accountDAO: AccountDAO): ViewModel() {
    // DateTime 타입의 MutableLiveData
    val _selectedDate = MutableLiveData<DateTime>()

    val selectedDate: LiveData<DateTime>
        get() = _selectedDate

    fun selectDate(dateTime: DateTime) {
        _selectedDate.value = dateTime
    }
}