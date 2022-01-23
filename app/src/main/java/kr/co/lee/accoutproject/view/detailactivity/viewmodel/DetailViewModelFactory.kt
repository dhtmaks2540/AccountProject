package kr.co.lee.accoutproject.view.detailactivity.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.lee.accoutproject.room.AccountDatabase

class DetailViewModelFactory(
    private val app: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val accountDao = AccountDatabase.get(app).accountDAO()

            @Suppress("UNCHECKED_CAST")
            // ViewModel을 생성하여 반환
            return DetailViewModel(accountDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}