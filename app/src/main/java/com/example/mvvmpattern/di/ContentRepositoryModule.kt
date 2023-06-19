package com.example.mvvmpattern.di


import com.example.mvvmpattern.room.AppDatabase
import com.example.mvvmpattern.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContentRepositoryModule {
    @Singleton
    @Provides
    fun provideContentDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}