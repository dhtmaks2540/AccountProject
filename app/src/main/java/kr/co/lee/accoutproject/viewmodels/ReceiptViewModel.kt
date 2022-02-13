package kr.co.lee.accoutproject.viewmodels

import android.accounts.Account
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.data.AccountEntity
import kr.co.lee.accoutproject.data.AccountRepository
import kr.co.lee.accoutproject.utilities.ioThread
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _accountAndType = MutableLiveData<AccountAndType>()

    val accountAndType: LiveData<AccountAndType>
        get() = _accountAndType

    fun setAccountAndType(accountAndType: AccountAndType) {
        _accountAndType.value = accountAndType
    }

    fun deleteAccount() {
        ioThread {
            accountRepository.removeAccount(accountAndType.value?.account)
        }
    }
}