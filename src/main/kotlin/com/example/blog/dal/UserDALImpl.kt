package com.example.blog.dal

import com.example.blog.model.LoginModel
import com.example.blog.model.User
import com.example.blog.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class UserDALImpl : UserDAL {

    @Autowired
    private val mongoTemplate: MongoTemplate? = null

//    @Autowired
//    private val bCryptPasswordEncoder: PasswordEncoder? = null

    override fun getAllUsers(): List<User?>? {
        val query = Query()
        query.addCriteria(Criteria.where("status").`is`(1))
        return mongoTemplate!!.find(query, User::class.java)
    }

    override fun getUserById(userId: String?): User? {
        val query = Query()
        query.addCriteria(Criteria.where("_id").`is`(userId))
        return mongoTemplate?.findOne(query, User::class.java)
    }

    override fun getUserByEmail(email: String?): User? {
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(email))
        return mongoTemplate?.findOne(query, User::class.java)
    }

    override fun validateSession(session: String?): User? {
        val query = Query()
        query.addCriteria(Criteria.where("session").`is`(session))
        val user = mongoTemplate?.findOne(query, User::class.java)
        print("$user")
        return mongoTemplate?.findOne(query, User::class.java)
    }

    override fun deleteUser(userId: String?): String? {
        val user = getUserById(userId)
        mongoTemplate?.remove(user!!)
        return "${user!!.name} deleted successfully."
    }

    override fun getUserByName(name: String?): List<User?>? {
        val regex = Criteria.where("name").regex(".*${name}.*", "i")
        return mongoTemplate!!.find(Query().addCriteria(regex), User::class.java)
    }

    override fun addNewUser(user: User?): User? {
        user?.password = Utils.md5(user?.password.toString())
        user?.creationDate = Date()
        user?.updateDate = Date()
        return mongoTemplate?.save(user!!)
    }

    override fun login(loginModel: LoginModel?): User? {
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(loginModel!!.email))

        val update = Update()
        update["session"] = 100
        update["updateDate"] = Date()

        return mongoTemplate!!.findAndModify(
            query, update,
            FindAndModifyOptions().returnNew(true), User::class.java
        )
    }

}