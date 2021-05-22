package com.example.blog.controller

import com.example.blog.User
import com.example.blog.model.Comment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class UserController {

    @RequestMapping("/rest")
    fun index() = "This is home!"

    @RequestMapping("/comment")
    fun getComment(): ResponseEntity<Map<String, Any>> {
        val comment = Comment(
            author = "codebeast",
            content = "I'm so loving Kotlin",
            created = Instant.now()
        )
        val map = HashMap<String, Any>()
        map["status"] = true
        map["reason"] = comment
        return ResponseEntity(map, HttpStatus.OK)
    }

    @RequestMapping("/search")
    fun search(@RequestParam(name = "name") userName: String) : ResponseEntity<Map<String, Any>>  {
        return try {
            val map = HashMap<String, Any>()
            map["status"] = true
            map["message"] = "success"
            map["name"] = User().getUser(userName)
            ResponseEntity(map, HttpStatus.OK)
        } catch (e : Exception) {
            val map = HashMap<String, Any>()
            map["status"] = false
            map["message"] = e.message.toString()
            ResponseEntity(map, HttpStatus.OK)
        }
    }

    @RequestMapping("/comment/{value}")
    fun findComment(@PathVariable("value") value: String) : String
            = value

}