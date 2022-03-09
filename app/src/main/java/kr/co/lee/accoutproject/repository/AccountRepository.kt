package kr.co.lee.accoutproject.repository

import kr.co.lee.accoutproject.persistence.AccountDAO
import kr.co.lee.accoutproject.model.AccountEntity
import javax.inject.Inject
import javax.inject.Singleton

// AccountDAO를 관리하는 Repository
@Singleton
class AccountRepository @Inject constructor(
    private val accountDao: AccountDAO
) {
    // AccountEntity 삭제
    fun removeAccount(account: AccountEntity?) {
        accountDao.deleteAccount(account)
    }

    fun getAccounts(year: Int, month: Int, day: Int) =
        accountDao.getMonthAccount(year, month, day)

    // AccountEntity 획득
    fun getAccounts(year: Int, month: Int) =
        accountDao.getMonthAccount(year, month)

    // AccountEntity 추가
    fun insertAccount(account: AccountEntity) {
        accountDao.insertAccount(account)
    }

    // AccountEntity 수정
    fun updateAccount(account: AccountEntity) {
        accountDao.updateAccount(account)
    }
}