package kr.co.lee.accoutproject.persistence

import androidx.room.*
import kr.co.lee.accoutproject.model.AccountAndType
import kr.co.lee.accoutproject.model.AccountEntity

// AccontEntity DAO
@Dao
interface AccountDAO {
    // AccountEntity INSERT
    @Insert
    fun insertAccount(accountEntity: AccountEntity)

    // AccountEntity DELETE
    @Delete
    fun deleteAccount(accountEntity: AccountEntity?)

    // 하루 정보
//    @Query("SELECT * FROM accounts WHERE year = (:year) and month = (:month) and day = (:day)")
//    fun getMonthAccount(year: Int, month: Int, day: Int): List<AccountEntity>

    // AccountEntity UPDATE
    @Update
    fun updateAccount(accountEntity: AccountEntity)

    // AccountEntity SELECT(Month 기준)
    @Query("SELECT * FROM accounts INNER JOIN types ON types.type_set_id == accounts.type_id WHERE accounts.year = (:year) AND accounts.Month = (:month)")
    fun getMonthAccount(year: Int, month: Int): List<AccountAndType>

    // AccountEntity SELETE(Day 기준)
    @Query("SELECT * FROM accounts INNER JOIN types ON types.type_set_id == accounts.type_id " +
            "WHERE accounts.year = (:year) AND accounts.Month = (:month) AND accounts.day = (:day)")
    fun getMonthAccount(year: Int, month: Int, day: Int): List<AccountAndType>
}