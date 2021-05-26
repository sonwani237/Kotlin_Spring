package com.example.blog.controller

import com.example.blog.dal.PostDal
import com.example.blog.dal.UserDAL
import com.example.blog.model.Post
import com.example.blog.payload.response.ResponseModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping(value = ["/api/post"])
class PostController(val userDAL: UserDAL, val postDal: PostDal) {

    private val logger: Logger = LoggerFactory.getLogger(PostController::class.java)

    @PostMapping("/addPost")
    fun addPost(@Valid @RequestBody post: Post?): ResponseEntity<*> {
        return ResponseEntity.ok(
            ResponseModel(
                status = 200,
                message = "Success",
                response = postDal.addPost(post)
            )
        )
    }

    @GetMapping("/getPost")
    fun getPost(): ResponseEntity<*> {
        return ResponseEntity.ok(
            ResponseModel(
                status = 200,
                message = "Success",
                response = postDal.getPosts()
            )
        )
    }

}