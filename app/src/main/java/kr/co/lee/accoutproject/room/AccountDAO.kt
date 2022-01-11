package kr.co.lee.accoutproject.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AccountDAO {
    @Insert
    fun insert(accountEntity: AccountEntity)

    @Query("SELECT * FROM accounts WHERE year = (:year) and month = (:month) and day = (:day)")
    fun getMonthAccount(year: Int, month: Int, day: Int): List<AccountEntity>
}