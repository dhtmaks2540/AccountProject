package kr.co.lee.accoutproject.view.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.lee.accoutproject.room.AccountDatabase

class MainViewModelFactory(
    private val app: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val accountDao = AccountDatabase.get(app).accountDAO()

            @Suppress("UNCHECKED_CAST")
            return MainViewModel(accountDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}