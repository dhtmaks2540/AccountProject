package kr.co.lee.accoutproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AccountDAO {
    // 내역 추가
    @Insert
    fun insertAccount(accountEntity: AccountEntity)

    // 내역 삭제
    @Delete
    fun deleteAccount(accountEntity: AccountEntity)

    // 하루 정보
    @Query("SELECT * FROM accounts WHERE year = (:year) and month = (:month) and day = (:day)")
    fun getMonthAccount(year: Int, month: Int, day: Int): List<AccountEntity>

    @Query("SELECT * FROM accounts WHERE year = (:year) and Month = (:month)")
    fun getMonthAccount(year: Int, month: Int): List<AccountEntity>

    @Query("SELECT * FROM accounts")
    fun getMonthAccount(): List<AccountEntity>
}