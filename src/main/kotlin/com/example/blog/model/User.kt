package com.example.blog.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.collections.HashSet


@Document(collection = "users")
class User(
    @NotBlank
    @Size(max = 20)
    var username: String?,
    @NotBlank
    @Size(max = 50)
    @Email
    var email: String?,
    @NotBlank
    @Size(max = 120)
    var password: String?
) {
    @Id
    var _id: String? = null

    var creationDate: Date? = Date()
    var updateDate: Date? = Date()

    @DBRef
    var roles: Set<Role> = HashSet()

}