package kr.co.lee.accoutproject.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.lee.accoutproject.data.AppDatabase

class DetailViewModelFactory(
    private val app: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val typeDao = AppDatabase.get(app).typeDAO()

            @Suppress("UNCHECKED_CAST")
            // ViewModel을 생성하여 반환
            return DetailViewModel(typeDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}