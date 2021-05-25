package com.example.blog.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "roles")
class Role {
    @Id
    private var id: String? = null

    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private var role: String? = null
}