package com.example.blog.dal

import com.example.blog.model.Post
import com.example.blog.repository.UserRepository
import com.example.blog.security.services.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.aggregation.MatchOperation
import org.springframework.data.mongodb.core.aggregation.UnwindOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class PostDALImpl : PostDal {

    @Autowired
    private val mongoTemplate: MongoTemplate? = null

    @Autowired
    var userRepository: UserRepository? = null

    override fun getPosts(): List<Post?> {
        val query = Query()
        query.addCriteria(Criteria.where("status").`is`(1)).with(Sort.by(Sort.Direction.DESC, "updateDate"))
        val post = mongoTemplate!!.find(query, Post::class.java)

        for(i in 0 until post.size) {
            post[i].user = userRepository!!.findById(post[i].userId)
        }
        return post
    }

    override fun addPost(post: Post?): Post? {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        post!!.userId = (userDetails as UserDetailsImpl).id
        post.creationDate = Date()
        post.updateDate = Date()
        return mongoTemplate!!.save(post)
    }

}