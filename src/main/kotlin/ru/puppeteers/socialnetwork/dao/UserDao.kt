package ru.puppeteers.socialnetwork.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.puppeteers.socialnetwork.api.dto.RegisterRequest
import ru.puppeteers.socialnetwork.api.dto.RegisterResponse
import ru.puppeteers.socialnetwork.api.dto.UserInfoResponse
import ru.puppeteers.socialnetwork.api.dto.UserSearchRequest
import ru.puppeteers.socialnetwork.entity.User
import ru.puppeteers.socialnetwork.exception.AccountAlreadyExistsException
import java.sql.ResultSet
import java.util.*

@Service
class UserDao(
    val genderDao: GenderDao,
    val template: NamedParameterJdbcTemplate
) {

    @Transactional
    fun register(request: RegisterRequest): RegisterResponse {
        try {
            checkIfAccountAlreadyExist(request)
            val gender = genderDao.findById(request.gender)
            val uuid = template.queryForObject(
                "insert into users(enabled, password) values (true, :passwd) returning id",
                mapOf("passwd" to request.password),
                UUID::class.java
            )

            template.update(
                "insert into user_info(id, email, first_name, second_name, gender_id, birth_date, city, " +
                        "interests, is_celebrity) values (:id, :email, :firstName, :secondName, :gender, :birthDate, " +
                        ":city, :interests, :is_celebrity)",
                mapOf(
                    "id" to uuid,
                    "email" to request.email,
                    "firstName" to request.firstName,
                    "secondName" to request.secondName,
                    "gender" to gender.id,
                    "birthDate" to request.birthDate,
                    "city" to request.city,
                    "interests" to request.interests.toTypedArray(),
                    "is_celebrity" to request.isCelebrity
                )
            )
            println("User registered with id: $uuid")
            return RegisterResponse(true, null)
        } catch (e: Exception) {
            println(e)
            return RegisterResponse(false, listOf(e.message))
        }
    }

    @Transactional(readOnly = true)
    fun getUserByEmail(email: String): User? {
        return template.queryForObject(
            "select ui.id, u.enabled, u.password, ui.email, ui.first_name, ui.second_name, " +
                    "ui.birth_date, ui.city, ui.interests, ui.is_celebrity from user_info ui inner join users u " +
                    "on u.id = ui.id where ui.email = :email",
            mapOf("email" to email)
        ) { rs, _ ->
            buildUser(rs)
        }
    }

    @Transactional(readOnly = true)
    fun getUserById(id: UUID): User? {
        return template.queryForObject(
            "select ui.id, u.enabled, u.password, ui.email, ui.first_name, ui.second_name, " +
                    "ui.birth_date, ui.city, ui.interests, ui.is_celebrity from user_info ui inner join users u " +
                    "on u.id = ui.id where ui.id = :id",
            mapOf("id" to id)
        ) { rs, _ ->
            buildUser(rs)
        }
    }

    @Transactional(readOnly = true)
    fun searchByFirstAndLastName(searchRequest: UserSearchRequest): List<UserInfoResponse> {
        return template.query(
            "select id, email, first_name, second_name, birth_date, city, interests, is_celebrity from user_info " +
                    "where first_name like :first_name and second_name like :second_name order by id",
            mapOf(
                "first_name" to searchRequest.firstName + "%",
                "second_name" to searchRequest.lastName + "%"
            )
        ) { rs, _ ->
            buildUserInfo(rs)
        }
    }

    private fun checkIfAccountAlreadyExist(request: RegisterRequest) {
        val isAccountExists = template.queryForObject(
            "select exists(select u.id from user_info u where u.email = :email)",
            mapOf("email" to request.email),
            Boolean::class.java
        )

        if (isAccountExists != null && isAccountExists) {
            throw AccountAlreadyExistsException("Account with email: ${request.email} already exists")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun buildUserInfo(rs: ResultSet): UserInfoResponse {
        return UserInfoResponse(
            rs.getString("email"),
            rs.getString("first_name"),
            rs.getString("second_name"),
            rs.getDate("birth_date"),
            rs.getString("city"),
            setOf(*rs.getArray("interests").array as Array<String>),
            rs.getBoolean("is_celebrity")
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun buildUser(rs: ResultSet): User {
        return User(
            rs.getObject("id") as UUID,
            rs.getString("email"),
            rs.getString("password"),
            rs.getBoolean("enabled"),
            rs.getString("first_name"),
            rs.getString("second_name"),
            rs.getDate("birth_date"),
            rs.getString("city"),
            setOf(*rs.getArray("interests").array as Array<String>),
            rs.getBoolean("is_celebrity")
        )
    }
}