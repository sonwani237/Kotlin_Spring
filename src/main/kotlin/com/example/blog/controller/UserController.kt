package com.example.blog.controller

import com.example.blog.dal.UserDAL
import com.example.blog.model.LoginModel
import com.example.blog.model.ResponseModel
import com.example.blog.model.User
import com.example.blog.utils.Utils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = ["/api/user"])
class UserController(val userDAL: UserDAL) {

    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping(value = ["/register"])
    fun addNewUsers(@RequestBody user: User?): ResponseModel? {
        return if (userDAL.getUserByEmail(user!!.email) != null) {
            ResponseModel(status = false, invalidResponse = false, message = "Email already registered")
        } else {
            ResponseModel(
                status = true,
                invalidResponse = false,
                message = "Success",
                response = userDAL.addNewUser(user)
            )
        }
    }

    @PostMapping(value = ["/login"])
    fun login(@RequestBody loginModel: LoginModel?): ResponseModel? {
        val user = userDAL.getUserByEmail(loginModel!!.email)
        return if (user == null) {
            ResponseModel(status = false, invalidResponse = false, message = "Email not registered")
        } else {
            logger.info("this is a info message");
            print("-------")
            if (Utils.md5(user.password!!) != Utils.md5(loginModel.password)) {
                ResponseModel(false, invalidResponse = false, "Invalid password")
            } else {
                ResponseModel(
                    status = true,
                    invalidResponse = false,
                    message = "Success",
                    response = userDAL.login(loginModel)
                )
            }
        }
    }

    @PostMapping("/users")
    fun getUser(@RequestParam userId: String?, @RequestParam name: String?, @RequestBody user: User?): ResponseModel? {
        return when {
            userDAL.validateSession(user?.session) == null -> {
                ResponseModel(status = false, invalidResponse = true, message = "Invalid session")
            }
            userId.isNullOrBlank() && name.isNullOrBlank() -> {
                ResponseModel(
                    status = true,
                    invalidResponse = false,
                    message = "Success",
                    response = userDAL.getAllUsers()
                )
            }
            userId != null && userId.isNotEmpty() && userDAL.getUserById(userId) != null -> {
                ResponseModel(
                    status = true,
                    invalidResponse = false,
                    message = "Success",
                    response = userDAL.getUserById(userId)!!
                )
            }
            name != null && name.isNotEmpty() -> {
                ResponseModel(
                    status = true,
                    invalidResponse = false,
                    message = "Success",
                    response = userDAL.getUserByName(name)!!
                )
            }
            else -> {
                ResponseModel(status = false, invalidResponse = false, message = "No User Found")
            }
        }
    }

    @GetMapping("/delete/{userId}")
    fun deleteUser(@PathVariable userId: String?): ResponseModel? {
        return if (userId != null && userId.isNotEmpty() && userDAL.getUserById(userId) != null) {
            return ResponseModel(
                status = true,
                invalidResponse = false,
                message = "Success",
                userDAL.deleteUser(userId)
            )
        } else {
            ResponseModel(status = false, invalidResponse = false, message = "Invalid user Id")
        }
    }


}