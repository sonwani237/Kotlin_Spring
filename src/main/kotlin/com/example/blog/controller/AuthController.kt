package com.example.blog.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import com.example.blog.repository.UserRepository
import com.example.blog.repository.RoleRepository
import org.springframework.security.crypto.password.PasswordEncoder
import com.example.blog.security.jwt.JwtUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid
import com.example.blog.payload.request.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import com.example.blog.security.services.UserDetailsImpl
import org.springframework.security.core.GrantedAuthority
import java.util.stream.Collectors
import com.example.blog.payload.response.JwtResponse
import com.example.blog.payload.request.SignupRequest
import java.util.HashSet
import com.example.blog.model.ERole
import com.example.blog.payload.response.ResponseModel
import com.example.blog.model.Role
import com.example.blog.model.User
import java.lang.RuntimeException
import java.util.function.Consumer

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    var authenticationManager: AuthenticationManager? = null

    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var roleRepository: RoleRepository? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    @Autowired
    var jwtUtils: JwtUtils? = null

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?): ResponseEntity<*> {
        val authentication = authenticationManager!!.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest!!.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils!!.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
            .map { item: GrantedAuthority -> item.authority }
            .collect(Collectors.toList())
        return ResponseEntity.ok(
            ResponseModel(
                status = 200,
                message = "Sign In successfully!",
                JwtResponse(
                    jwt,
                    userDetails.id,
                    userDetails.username,
                    userDetails.email,
                    roles
                )
            )
        )
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignupRequest?): ResponseEntity<*> {
        if (userRepository!!.existsByUsername(signUpRequest!!.username)!!) {
            return ResponseEntity
                .badRequest()
                .body(
                    ResponseModel(
                        status = 400,
                        message = "Error: Username is already taken!"
                    )
                )
        }
        if (userRepository!!.existsByEmail(signUpRequest.email)!!) {
            return ResponseEntity
                .badRequest()
                .body(
                    ResponseModel(
                        status = 400,
                        message = "Error: Email is already in use!"
                    )
                )
        }
        // Create new user's account
        val user = User(
            signUpRequest.username,
            signUpRequest.email,
            encoder!!.encode(signUpRequest.password)
        )
        val strRoles = signUpRequest.roles
        val roles: MutableSet<Role> = HashSet()
        if (strRoles == null) {
            val userRole = roleRepository!!.findByName(ERole.ROLE_USER)!!
                .orElseThrow { RuntimeException("Error: Role is not found.") }!!
            roles.add(userRole)
        } else {
            strRoles.forEach(Consumer { role: String? ->
                when (role) {
                    "admin" -> {
                        val adminRole = roleRepository!!.findByName(ERole.ROLE_ADMIN)!!
                            .orElseThrow { RuntimeException("Error: Role is not found.") }!!
                        roles.add(adminRole)
                    }
                    "mod" -> {
                        val modRole = roleRepository!!.findByName(ERole.ROLE_MODERATOR)!!
                            .orElseThrow { RuntimeException("Error: Role is not found.") }!!
                        roles.add(modRole)
                    }
                    else -> {
                        val userRole = roleRepository!!.findByName(ERole.ROLE_USER)!!
                            .orElseThrow { RuntimeException("Error: Role is not found.") }!!
                        roles.add(userRole)
                    }
                }
            })
        }
        user.roles = roles
        userRepository!!.save(user)
        return ResponseEntity.ok(
            ResponseModel(
                status = 200,
                message = "User registered successfully!",
                user
            )
        )
    }
}