package kr.co.lee.accoutproject.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kr.co.lee.accoutproject.utility.ioThread

@Database(entities = [AccountEntity::class, TypeEntity::class], version = 1)
abstract class AccountDatabase: RoomDatabase() {
    abstract fun accountDAO(): AccountDAO

    companion object {
        private var instance: AccountDatabase? = null

        @Synchronized
        fun get(context: Context): AccountDatabase {
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccountDatabase::class.java, "account_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        fillInDb(context.applicationContext)
                    }
                }).build()
            }

            return instance!!
        }

        private fun fillInDb(context: Context) {
            ioThread {
                get(context).accountDAO().insertTypes(
                    typeList.map { TypeEntity(typeId = 0, typeForm = it[0].toInt(), typeName = it[1], typeImageName = it[2], typeColor = it[3]) }
                )
            }
        }
    }
}

// 타입 리스트
// 타입 형식, 타입 이름, 타입 아이콘 이름, 타입 색
// 형식은 0은 지출, 1은 입금
private val typeList = arrayListOf(
    listOf("1", "용돈", "baseline_account_balance_wallet_white_20", "#ffffff"),
    listOf("0", "생필품", "baseline_add_shopping_cart_white_20", "#ffffff"),
    listOf("1", "이월", "baseline_arrow_right_alt_white_20", "#ffffff"),
    listOf("0", "의류", "baseline_checkroom_white_20", "#ffffff"),
    listOf("0", "교통비", "baseline_directions_bus_white_20", "#fffffff"),
    listOf("0", "가전", "baseline_laptop_chromebook_white_20", "#fffffff"),
    listOf("0", "의료/건강", "baseline_medication_white_20", "#ffffff"),
    listOf("0", "교육", "baseline_menu_book_white_20", "#ffffff"),
    listOf("1", "월급", "baseline_money_white_20", "#ffffff"),
    listOf("0", "식비", "baseline_restaurant_menu_white_20", "#ffffff"),
    listOf("0", "저축", "baseline_savings_white_20", "#ffffff"),
    listOf("0", "통신비", "baseline_smartphone_white_20", "#ffffff"),
    listOf("0", "기타", "baseline_remove_white_20", "#ffffff"),
    listOf("1", "기타", "baseline_remove_white_20", "#ffffff")
)