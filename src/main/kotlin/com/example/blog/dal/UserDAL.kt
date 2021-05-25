package com.example.blog.dal

import com.example.blog.model.LoginModel
import com.example.blog.model.User

interface UserDAL {
    fun getAllUsers(): List<User?>?
    fun getUserById(userId: String?): User?
    fun getUserByEmail(email: String?): User?
    fun validateSession(session: String?): User?
    fun deleteUser(userId: String?): String?
    fun getUserByName(name: String?): List<User?>?
    fun addNewUser(user: User?): User?
    fun login(loginModel: LoginModel?): User?
}