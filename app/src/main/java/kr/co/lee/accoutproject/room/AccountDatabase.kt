package kr.co.lee.accoutproject.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AccountEntity::class, TypeEntity::class], version = 1)
abstract class AccountDatabase: RoomDatabase() {
    abstract fun accountDAO(): AccountDAO
}