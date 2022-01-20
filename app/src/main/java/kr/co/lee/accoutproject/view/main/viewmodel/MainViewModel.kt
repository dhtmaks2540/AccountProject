package kr.co.lee.accoutproject.view.main.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.lee.accoutproject.room.AccountDAO
import kr.co.lee.accoutproject.room.TypeEntity
import kr.co.lee.accoutproject.utility.ioThread
import org.joda.time.DateTime
import java.util.*

class MainViewModel(private val accountDao: AccountDAO): ViewModel() {
    // DateTime 타입의 MutableLiveData
    private val mutableSelectedItem = MutableLiveData<DateTime>()
    private val _typeEntity = MutableLiveData<List<TypeEntity>>()

    val selectedItem: LiveData<DateTime>
        get() = mutableSelectedItem

    val typeEntity: LiveData<List<TypeEntity>>
        get() = _typeEntity

    fun selectItem(dateItem: DateTime) {
        // setValue를 통해 값 설정
        mutableSelectedItem.value = dateItem
    }

    fun test() {
        ioThread {
            _typeEntity.postValue(accountDao.getTypes(0))
        }
    }
}