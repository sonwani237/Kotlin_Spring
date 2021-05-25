package com.example.blog.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class User {
    @Id var _id: String? = null
    @Indexed(unique = true)var email: String? = null
    var name: String? = null
    var password: String? = null
    var session: String? = null
    var status: Int? = 1
    var creationDate: Date? = null
    var updateDate: Date? = null
    @DBRef val roles: Set<Role>? = null
}
