package com.example.blog.repository

import com.example.blog.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*


interface UserRepository : MongoRepository<User?, String?> {
    fun findByUsername(username: String?): Optional<User?>?
    fun findById(_id: String?): User?
    fun existsByUsername(username: String?): Boolean?
    fun existsByEmail(email: String?): Boolean?
    override fun findAll(): MutableList<User?>
}