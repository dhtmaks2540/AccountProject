package kr.co.lee.accoutproject.persistence

import androidx.room.*
import kr.co.lee.accoutproject.model.AccountAndType
import kr.co.lee.accoutproject.model.AccountEntity

@Dao
interface AccountDAO {
    // 내역 추가
    @Insert
    fun insertAccount(accountEntity: AccountEntity)

    // 내역 삭제
    @Delete
    fun deleteAccount(accountEntity: AccountEntity?)

    // 하루 정보
//    @Query("SELECT * FROM accounts WHERE year = (:year) and month = (:month) and day = (:day)")
//    fun getMonthAccount(year: Int, month: Int, day: Int): List<AccountEntity>

    // 내역 업데이트
    @Update
    fun updateAccount(accountEntity: AccountEntity)

    @Query("SELECT * FROM accounts INNER JOIN types ON types.type_set_id == accounts.type_id WHERE accounts.year = (:year) AND accounts.Month = (:month)")
    fun getMonthAccount(year: Int, month: Int): List<AccountAndType>

    @Query("SELECT * FROM accounts INNER JOIN types ON types.type_set_id == accounts.type_id " +
            "WHERE accounts.year = (:year) AND accounts.Month = (:month) AND accounts.day = (:day)")
    fun getMonthAccount(year: Int, month: Int, day: Int): List<AccountAndType>
}