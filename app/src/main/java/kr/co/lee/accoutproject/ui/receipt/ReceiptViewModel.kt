package kr.co.lee.accoutproject.ui.receipt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.lee.accoutproject.model.AccountAndType
import kr.co.lee.accoutproject.repository.AccountRepository
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

    // Account 삭제
    fun deleteAccount() {
        ioThread {
            accountRepository.removeAccount(accountAndType.value?.account)
        }
    }

    // Account 수정
    fun updateAccount(moneyString: String, content: String) {
        val money = moneyString.filter { it.isDigit() }.toLong()
        val accountEntity = accountAndType.value?.account?.let {
            it.copy(money = money, content = content)
        }

        ioThread {
            accountRepository.updateAccount(accountEntity!!)
        }
    }
}