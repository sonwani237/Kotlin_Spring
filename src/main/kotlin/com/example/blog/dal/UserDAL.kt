package com.example.blog.dal

import com.example.blog.model.User

interface UserDAL {
    fun getAllUsers(): List<User?>?
    fun getUserById(userId: String?): List<User?>??
    fun getUserByEmail(email: String?): User?
    fun deleteUser(userId: String?): String?
    fun getUserByName(name: String?): List<User?>?
}