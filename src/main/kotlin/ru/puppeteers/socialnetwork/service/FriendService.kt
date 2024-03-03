package ru.puppeteers.socialnetwork.service

import org.springframework.stereotype.Service
import ru.puppeteers.socialnetwork.api.dto.BaseResponse
import ru.puppeteers.socialnetwork.api.dto.FriendRequest
import ru.puppeteers.socialnetwork.dao.FriendDao
import java.util.*

@Service
class FriendService(
    val friendDao: FriendDao
) {

    fun addFriend(userId: UUID, friendId: UUID): BaseResponse {
        friendDao.addFriend(userId, friendId);
        return BaseResponse(true, null)
    }

    fun removeFriend(userId: UUID, friendId: UUID): BaseResponse {
        friendDao.removeFriend(userId, friendId)
        return BaseResponse(true, null)
    }
}