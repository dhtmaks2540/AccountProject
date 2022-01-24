package kr.co.lee.accoutproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.lee.accoutproject.data.AccountDAO
import org.joda.time.DateTime

// 인자로 DAO 객체를 받는 ViewModel
class MainViewModel(private val accountDao: AccountDAO): ViewModel() {
    // DateTime 타입의 MutableLiveData
    private val mutableSelectedItem = MutableLiveData<DateTime>()

    val selectedItem: LiveData<DateTime>
        get() = mutableSelectedItem

    fun selectItem(dateItem: DateTime) {
        // setValue를 통해 값 설정
        mutableSelectedItem.value = dateItem
    }
}