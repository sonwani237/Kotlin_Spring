package com.example.blog.payload.response

data class ResponseModel(val status: Int, val message: String, var response: Any? = null)
