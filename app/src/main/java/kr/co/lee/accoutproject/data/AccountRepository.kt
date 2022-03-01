package kr.co.lee.accoutproject.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val accountDao: AccountDAO
) {
    fun removeAccount(account: AccountEntity?) {
        accountDao.deleteAccount(account)
    }

    fun getAccounts(year: Int, month: Int, day: Int) =
        accountDao.getMonthAccount(year, month, day)

    fun getAccounts(year: Int, month: Int) =
        accountDao.getMonthAccount(year, month)

    fun insertAccount(account: AccountEntity) {
        accountDao.insertAccount(account)
    }

    fun updateAccount(account: AccountEntity) {
        accountDao.updateAccount(account)
    }
}