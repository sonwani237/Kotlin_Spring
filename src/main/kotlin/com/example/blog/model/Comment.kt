package com.example.blog.model

import java.time.Instant

data class Comment(
    val author: String,
    val content: String,
    val created: Instant
)
