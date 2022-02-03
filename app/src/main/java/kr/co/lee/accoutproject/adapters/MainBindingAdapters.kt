package kr.co.lee.accoutproject.adapters

import android.view.MenuItem
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import org.joda.time.DateTime

//// 선택된 아이템을 변경하기 위한 BindingAdapter
//@BindingAdapter("selected_item")
//fun BottomNavigationView.changeSelectedItem(itemId: Int?) {
//    if(itemId != null && itemId != selectedItemId) {
//        selectedItemId = itemId
//    }
//}
//
//// 바텀 네비게이션 뷰에서 현재 선택된 아이템의 id를 얻기 위한 InverseBindingAdapter
//@InverseBindingAdapter(attribute = "app:selectedItem", event = "android:selectedAttrChanged")
//fun BottomNavigationView.getSelectedItem(): Int {
//    return selectedItemId
//}
//
//// 바텀 네비게이션 뷰에 선택된 아이템의 변화를 감지하는 Binding Adapter 함수
//@BindingAdapter(value = ["app:afterItemChanged", "android:selectedAttrChanged"], requireAll = false)
//fun BottomNavigationView.addOnItemSelectedListener(
//    afterItemChanged: OnAfterNavigationItemChangeListener?,
//    attrChanged: InverseBindingListener?
//) {
//    val listener = object : NavigationBarView.OnItemSelectedListener {
//        override fun onNavigationItemSelected(item: MenuItem): Boolean {
//            if(item.itemId != selectedItemId) {
//                item.isChecked = false
//            }
//            afterItemChanged?.afterItemChanged(item.itemId)
//            attrChanged?.onChange()
//            return false
//        }
//    }
//
//    setOnItemSelectedListener(listener)
//}
//
//interface OnAfterNavigationItemChangeListener {
//    fun afterItemChanged(itemId: Int)
//}