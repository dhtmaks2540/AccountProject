package kr.co.lee.accoutproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kr.co.lee.accoutproject.utilities.ioThread

// Database 클래스 - DAO를 생성하는 역할
@Database(entities = [AccountEntity::class, TypeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun accountDAO(): AccountDAO
    abstract fun typeDAO(): TypeDAO

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

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
        // 타입 테이블 추가하는 메소드
        private fun fillInDb(context: Context) {
            ioThread {
                getInstance(context).typeDAO().insertTypes(
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
    listOf("1", "용돈", "baseline_account_balance_wallet_white_20", "#81C147"),
    listOf("0", "생필품", "baseline_add_shopping_cart_white_20", "#FF7F00"),
    listOf("1", "이월", "baseline_arrow_right_alt_white_20", "#967BDC"),
    listOf("0", "의류", "baseline_checkroom_white_20", "#79ECFF"),
    listOf("0", "교통비", "baseline_directions_bus_white_20", "#FF0C88"),
    listOf("0", "가전", "baseline_laptop_chromebook_white_20", "#4B89DC"),
    listOf("0", "의료/건강", "baseline_medication_white_20", "#3CB371"),
    listOf("0", "교육", "baseline_menu_book_white_20", "#967BDC"),
    listOf("1", "월급", "baseline_money_white_20", "#6FADCF"),
    listOf("0", "식비", "baseline_restaurant_menu_white_20", "#82FB02"),
    listOf("0", "저축", "baseline_savings_white_20", "#D6B534"),
    listOf("0", "통신비", "baseline_smartphone_white_20", "#FFD700"),
    listOf("0", "기타", "baseline_remove_white_20", "#9B111E"),
    listOf("1", "기타", "baseline_remove_white_20", "#9B111E")
)