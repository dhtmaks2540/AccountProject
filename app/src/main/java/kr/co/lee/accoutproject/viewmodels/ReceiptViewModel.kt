package kr.co.lee.accoutproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.lee.accoutproject.data.AccountAndType
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor() : ViewModel() {
    private val _accountAndType = MutableLiveData<AccountAndType>()

    val accountAndType: LiveData<AccountAndType>
        get() = _accountAndType

    fun setAccountAndType(accountAndType: AccountAndType) {
        _accountAndType.value = accountAndType
    }
}