package com.example.blog.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = ["/"])
class HtmlController {

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    fun blog(model: Model): String {
        model["title"] = "Blog"
        return "index"
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun admin(model: Model): String {
        model["title"] = "Admin"
        return "admin"
    }

}