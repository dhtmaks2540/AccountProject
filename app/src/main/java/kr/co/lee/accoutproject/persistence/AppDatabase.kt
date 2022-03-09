package kr.co.lee.accoutproject.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kr.co.lee.accoutproject.model.AccountEntity
import kr.co.lee.accoutproject.model.TypeEntity
import kr.co.lee.accoutproject.utilities.ioThread
import kr.co.lee.accoutproject.utilities.typeList

// Database 클래스 - DAO를 생성하는 역할
@Database(entities = [AccountEntity::class, TypeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun accountDAO(): AccountDAO
    abstract fun typeDAO(): TypeDAO

    companion object {
        private var instance: AppDatabase? = null

        // RoobDatabase 객체 획득
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // RoomDatabase 생성
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "account_database")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        fillInDb(context.applicationContext)
                    }
                }).build()
        }

//        @Synchronized
//        fun get(context: Context): AppDatabase {
//            if(instance == null) {
//                instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java, "account_database"
//                ).addCallback(object : RoomDatabase.Callback() { // 콜백 추가
//                    // RoomDatabase 객체가 생성되면 호추로디는 콜백 메소드
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                        // fillInDb 메소드 호출
//                        super.onCreate(db)
//                        fillInDb(context.applicationContext)
//                    }
//                }).build()
//            }
//
//            return instance!!
//        }
//
        // TypeEntity 추가하는 메소드
        private fun fillInDb(context: Context) {
            ioThread {
                getInstance(context).typeDAO().insertTypes(
                    typeList.map { TypeEntity(typeId = 0, typeForm = it[0].toInt(), typeName = it[1], typeImageName = it[2], typeColor = it[3]) }
                )
            }
        }
    }
}