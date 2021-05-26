package com.example.blog.dal

import com.example.blog.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository


@Repository
class UserDALImpl : UserDAL {

    @Autowired
    private val mongoTemplate: MongoTemplate? = null

    override fun getAllUsers(): List<User?>? {
        val query = Query()
        query.addCriteria(Criteria.where("status").`is`(1))
        return mongoTemplate!!.find(query, User::class.java)
    }

    override fun getUserById(userId: String?): List<User?>? {
        val query = Query()
        query.addCriteria(Criteria.where("_id").`is`(userId))
        return mongoTemplate?.find(query, User::class.java)
    }

    override fun getUserByEmail(email: String?): User? {
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(email))
        return mongoTemplate?.findOne(query, User::class.java)
    }

    override fun deleteUser(userId: String?): String? {
        val user = getUserById(userId)
        mongoTemplate?.remove(user!!)
        return "${user!![0]!!.username} deleted successfully."
    }

    override fun getUserByName(name: String?): List<User?>? {
        val regex = Criteria.where("name").regex(".*${name}.*", "i")
        return mongoTemplate!!.find(Query().addCriteria(regex), User::class.java)
    }


}