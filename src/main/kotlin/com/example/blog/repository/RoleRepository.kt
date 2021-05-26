package com.example.blog.repository

import com.example.blog.model.ERole
import com.example.blog.model.Role
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*


interface RoleRepository : MongoRepository<Role?, String?> {
    fun findByName(name: ERole?): Optional<Role?>?
}