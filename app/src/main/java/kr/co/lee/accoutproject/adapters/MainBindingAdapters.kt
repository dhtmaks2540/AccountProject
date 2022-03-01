package kr.co.lee.accoutproject.adapters

import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.co.lee.accoutproject.viewmodels.MainViewModel

@BindingAdapter("onItemSelectedListener")
fun setOnNavigationItemSelectedListener(view: BottomNavigationView, viewModel: MainViewModel) {
    view.setOnItemSelectedListener { menuItem ->
        viewModel.setCurrentPage(menuItem.itemId)
    }
}