package com.example.blog.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Document(collection = "post")
class Post {
    @Id
    var _id: String? = null
    @NotNull @NotBlank @Size(max = 20)
    var name: String? = null
    @NotNull @NotBlank
    var description:String? = null
    var status: Int? = 1
    var userId: String? = null
    var creationDate: Date? = null
    var updateDate: Date? = null
    var user: User? = null
}