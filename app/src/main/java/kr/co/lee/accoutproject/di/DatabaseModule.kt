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

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideAccountDao(appDatabase: AppDatabase): AccountDAO {
        return appDatabase.accountDAO()
    }

    @Provides
    fun provideTypeDao(appDatabase: AppDatabase): TypeDAO {
        return appDatabase.typeDAO()
    }
}