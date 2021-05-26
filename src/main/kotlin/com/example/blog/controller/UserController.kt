package com.example.blog.controller

import com.example.blog.dal.UserDAL
import com.example.blog.payload.response.ResponseModel
import com.example.blog.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = ["/api/user"])
class UserController(val userDAL: UserDAL) {

    @Autowired
    var userRepository: UserRepository? = null

    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping("/users")
    fun getUser(@RequestParam userId: String?, @RequestParam name: String?): ResponseEntity<*> {
        return when {
            userId.isNullOrBlank() && name.isNullOrBlank() -> {
                ResponseEntity.ok(
                    ResponseModel(
                        status = 200,
                        message = "Success",
                        response = userRepository!!.findAll(Sort.by(Sort.Direction.DESC, "updateDate"))
                    )
                )
            }
            userId != null && userId.isNotEmpty() && userDAL.getUserById(userId) != null -> {
                ResponseEntity.ok(
                    ResponseModel(
                        status = 200,
                        message = "Success",
                        response = userRepository!!.findById(userId)
                    )
                )
            }
            name != null && name.isNotEmpty() -> {
                ResponseEntity.ok(
                    ResponseModel(
                        status = 200,
                        message = "Success",
                        response = userDAL.getUserByName(name)!!
                    )
                )
            }
            else -> {
                ResponseEntity.ok(ResponseModel(status = 200, message = "No User Found"))
            }
        }
    }

    @GetMapping("/delete/{userId}")
    fun deleteUser(@PathVariable userId: String?): ResponseEntity<*> {
        return if (userId != null && userId.isNotEmpty() && userDAL.getUserById(userId) != null) {
            return ResponseEntity.ok(
                ResponseModel(
                    status = 200,
                    message = "Success",
                    userDAL.deleteUser(userId)
                )
            )
        } else {
            ResponseEntity.ok(ResponseModel(status = 200, message = "Invalid user Id"))
        }
    }


}