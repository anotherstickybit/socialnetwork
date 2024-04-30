package ru.puppeteers.socialnetwork.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.puppeteers.socialnetwork.api.dto.BaseResponse
import ru.puppeteers.socialnetwork.api.dto.FriendRequest
import ru.puppeteers.socialnetwork.entity.User
import ru.puppeteers.socialnetwork.exception.FriendAddException
import ru.puppeteers.socialnetwork.exception.FriendRemoveException
import ru.puppeteers.socialnetwork.exception.FriendsReadException
import java.util.UUID

@Service
class FriendDao(
    val template: NamedParameterJdbcTemplate,
    val userDao: UserDao
) {

    @Transactional
    fun addFriend(userId: UUID, friendId: UUID) {
        try {
            template.update(
                "insert into friendship(user_id, friend_id) values (:user_id, :friend_id)",
                mapOf(
                    "user_id" to userId,
                    "friend_id" to friendId
                )
            )
            template.update(
                "insert into friendship(user_id, friend_id) values (:user_id, :friend_id)",
                mapOf(
                    "user_id" to friendId,
                    "friend_id" to userId
                )
            )
        } catch (e: Exception) {
            throw FriendAddException("Friend adding exception", e)
        }
    }

    @Transactional
    fun removeFriend(userId: UUID, friendId: UUID) {
        try {
            template.update(
                "delete from friendship where user_id = :user_id and friend_id = :friend_id",
                mapOf(
                    "user_id" to userId,
                    "friend_id" to friendId
                )
            )
            template.update(
                "delete from friendship where user_id = :friend_id and friend_id = :user_id",
                mapOf(
                    "user_id" to userId,
                    "friend_id" to friendId
                )
            )
        } catch (e: Exception) {
            throw FriendRemoveException("Friend removing exception", e)
        }
    }

    @Transactional(readOnly = true)
    fun getUserFriends(id: UUID): List<User> {
        try {
            val friendsList = arrayListOf<User>()
            val friendsIds = template.queryForList(
                "select friend_id from friendship where user_id = :userId",
                mapOf("userId" to id),
                UUID::class.java
            )
            friendsIds.forEach {
                friendsList.add(userDao.getUserById(it)!!)
            }

            return friendsList
        } catch (e: Exception) {
            throw FriendsReadException("Friends read exception", e)
        }
    }
}