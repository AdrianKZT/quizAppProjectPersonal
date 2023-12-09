package com.adrian.quizapppersonalproject.data.repo

import com.adrian.quizapppersonalproject.data.model.User

interface UserRepo {

    suspend fun addUser(user: User)

    suspend fun getUser(id: String): User?

    suspend fun removeUser()

    suspend fun updateUser(id: String, user: User)

    suspend fun getCurrentUser(): User?
}