package ru.puppeteers.socialnetwork.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.puppeteers.socialnetwork.api.dto.PostAddRequest
import ru.puppeteers.socialnetwork.api.dto.PostRemoveRequest
import ru.puppeteers.socialnetwork.api.dto.PostUpdateRequest
import ru.puppeteers.socialnetwork.entity.PostEntity
import ru.puppeteers.socialnetwork.exception.GetFeedException
import ru.puppeteers.socialnetwork.exception.PostAddException
import ru.puppeteers.socialnetwork.exception.PostRemoveException
import ru.puppeteers.socialnetwork.exception.PostUpdateException
import java.time.LocalDateTime
import java.util.*

@Service
class PostDao(
    val template: NamedParameterJdbcTemplate
) {

    @Transactional
    fun addPost(request: PostAddRequest): PostEntity {
        try {
            val creationDate = LocalDateTime.now()
            val postUUID = template.queryForObject(
                "insert into post(user_id, post_text, creation_date) values (:user_id, :text, :creation_date) " +
                        "returning id",
                mapOf(
                    "user_id" to request.userId,
                    "text" to request.text,
                    "creation_date" to creationDate
                ),
                UUID::class.java
            )
            return PostEntity(postUUID!!, request.userId, request.text, creationDate, creationDate)
        } catch (e: Exception) {
            throw PostAddException("Exception while adding post", e)
        }
    }

    @Transactional
    fun updatePost(request: PostUpdateRequest) {
        try {
            template.update(
                "update post set post_text = :text, updated_date = :updated_date where id = :id",
                mapOf(
                    "id" to request.id,
                    "updated_date" to LocalDateTime.now(),
                    "text" to request.newText
                )
            )
        } catch (e: Exception) {
            throw PostUpdateException("Exception while updating post", e)
        }
    }

    @Transactional
    fun removePost(request: PostRemoveRequest) {
        try {
            template.update(
                "delete from post where id = :id",
                mapOf("id" to request.id)
            )
        } catch (e: Exception) {
            throw PostRemoveException("Exception while post removing", e)
        }
    }

    @Transactional(readOnly = true)
    fun getUserPosts(id: UUID): List<PostEntity> {
        try {
            return template.query("select id, user_id, post_text, creation_date, updated_date from post " +
                    "where user_id = :id order by creation_date desc",
            mapOf("id" to id)
            ) { rs, _ ->
                PostEntity(
                    rs.getObject("id") as UUID,
                    rs.getObject("user_id") as UUID,
                    rs.getString("post_text"),
                    rs.getTimestamp("creation_date").toLocalDateTime(),
                    rs.getTimestamp("updated_date").toLocalDateTime()
                )
            }
        } catch (e: Exception) {
            throw GetFeedException("Exception while loading user posts", e)
        }
    }

    @Transactional(readOnly = true)
    fun getUserFeedForCache(id: UUID): List<PostEntity> {
        try {
            return template.query(
                "select id, p.user_id, post_text, creation_date, updated_date from post p inner join friendship f " +
                        "on p.user_id = f.friend_id where f.user_id = :id order by p.creation_date desc limit 1000",
                mapOf("id" to id)
            ) { rs, _ ->
                PostEntity(
                    rs.getObject("id") as UUID,
                    rs.getObject("user_id") as UUID,
                    rs.getString("post_text"),
                    rs.getTimestamp("creation_date").toLocalDateTime(),
                    rs.getTimestamp("updated_date").toLocalDateTime()
                )
            }
        } catch (e: Exception) {
            throw GetFeedException("Exception while loading user feed", e)
        }
    }
}