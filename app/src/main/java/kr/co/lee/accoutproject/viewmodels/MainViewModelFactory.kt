package kr.co.lee.accoutproject.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.lee.accoutproject.data.AppDatabase

// ViewModelProvider의 Factory 인터페이스를 구현하는 클래스
// ViewModel을 생성하는데 사용
class MainViewModelFactory(
    private val app: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val accountDao = AppDatabase.get(app).accountDAO()

            @Suppress("UNCHECKED_CAST")
            // ViewModel을 생성하여 반환
            return MainViewModel(accountDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}