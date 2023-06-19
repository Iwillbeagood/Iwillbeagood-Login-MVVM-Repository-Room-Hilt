package com.example.mvvmpattern.repository

import com.example.mvvmpattern.room.User
import com.example.mvvmpattern.room.UserDao
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: User) =
        userDao.insertUser(user)


    override suspend fun getUserByEmail(email: String): User? =
        userDao.getUserByEmail(email)
}