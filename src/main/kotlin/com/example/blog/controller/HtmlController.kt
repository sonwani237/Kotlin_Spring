package com.example.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {

    @GetMapping("/")
    fun blog(model: Model): String {
        model["title"] = "Blog"
        return "index"
    }

    @GetMapping("/admin")
    fun admin(model: Model): String {
        model["title"] = "Admin"
        return "admin"
    }

}