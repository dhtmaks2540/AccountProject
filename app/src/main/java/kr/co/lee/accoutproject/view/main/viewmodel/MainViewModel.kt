package kr.co.lee.accoutproject.view.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.joda.time.DateTime
import java.util.*

class MainViewModel: ViewModel() {
    private val mutableSelectedItem = MutableLiveData<DateTime>()
    val selectedItem: LiveData<DateTime>
        get() = mutableSelectedItem

    fun selectItem(dateItem: DateTime) {
        mutableSelectedItem.value = dateItem
    }
}