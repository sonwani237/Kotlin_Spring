package com.example.blog.model

data class ResponseModel(val status: Boolean, val invalidResponse: Boolean, val message: String, var response: Any? = null)
