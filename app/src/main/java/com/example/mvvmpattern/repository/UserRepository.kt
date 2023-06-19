package com.example.mvvmpattern.repository

import com.example.mvvmpattern.room.User

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun getUserByEmail(email: String): User?
}