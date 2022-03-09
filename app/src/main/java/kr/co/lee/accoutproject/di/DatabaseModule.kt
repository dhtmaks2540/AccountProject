package kr.co.lee.accoutproject.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.lee.accoutproject.persistence.AccountDAO
import kr.co.lee.accoutproject.persistence.AppDatabase
import kr.co.lee.accoutproject.persistence.TypeDAO
import javax.inject.Singleton

// RoomDB를 제공하는 Hilt Module
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    // RoomDatabase 제공
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    // AccountDAO 제공
    @Provides
    fun provideAccountDao(appDatabase: AppDatabase): AccountDAO {
        return appDatabase.accountDAO()
    }

    // TypeDAO 제공
    @Provides
    fun provideTypeDao(appDatabase: AppDatabase): TypeDAO {
        return appDatabase.typeDAO()
    }
}