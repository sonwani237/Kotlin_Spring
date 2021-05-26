package com.example.blog.dal

import com.example.blog.model.Post

interface PostDal {
    fun getPosts(): List<Post?>?
    fun addPost(post: Post?): Post?
}