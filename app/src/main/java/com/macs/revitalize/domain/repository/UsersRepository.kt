package com.macs.revitalize.domain.repository

interface UsersRepository {
    fun linkHabitToUser(habitKey: String, user: String)

    fun addAuthUser(email: String): String
}