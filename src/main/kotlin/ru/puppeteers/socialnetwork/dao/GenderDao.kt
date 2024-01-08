package ru.puppeteers.socialnetwork.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.puppeteers.socialnetwork.entity.Gender
import ru.puppeteers.socialnetwork.exception.GenderDoesNotExistsException
import java.sql.ResultSet

@Service
class GenderDao(
    val template: NamedParameterJdbcTemplate
) {

    @Transactional(readOnly = true)
    fun findById(id: Int): Gender {
        val gender = template.queryForObject(
            "select id, name from gender where id = :id",
            mapOf("id" to id)
        ) { rs, _ ->
            buildGender(rs)
        }

        if (gender == null) {
            throw GenderDoesNotExistsException("Provided gender with id: $id does not exists.")
        }

        return gender
    }

    @Transactional(readOnly = true)
    fun getGenderByName(name: String): Gender {
        val gender = template.queryForObject(
            "select id, name from gender where name = :name",
            mapOf("name" to name)
        ) { rs, _ ->
            buildGender(rs)
        }

        if (gender == null) {
            throw GenderDoesNotExistsException("Provided gender with name: $name does not exists.")
        }

        return gender
    }

    private fun buildGender(rs: ResultSet): Gender {
        return Gender(rs.getInt("id"), rs.getString("name"))
    }
}