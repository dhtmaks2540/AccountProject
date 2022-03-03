package kr.co.lee.accoutproject.utilities

import java.text.DecimalFormat
import java.util.concurrent.Executors

// 쓰레드 1개인 ExecutorService를 리턴합니다.
// 싱글 쓰레드에서 동작해야 하는 작업을 처리할 때 사용합니다.
private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

// background thread의 블록 코드를 작동시키는 Utility 메소드로 io/database를 위해 사용됨
// 함수를 인자로 받는 고차 함수 -> 함수를 인자로 받아서 싱글 스레드에서 주어진 함수를 실행함
// 백그라운드에서 작동하는 하나의 스레드이기에 코루틴을 사용해도 무방하다고 생각
fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}

enum class PageType(val title: String, val tag: String) {
    PAGE1("month_fragment", "month"),
    PAGE2("week_fragment", "week")
}

val decimalFormat = DecimalFormat("#,###")

// 타입 리스트
// 타입 형식, 타입 이름, 타입 아이콘 이름, 타입 색
// 형식은 0은 지출, 1은 입금
val typeList = arrayListOf(
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