package ru.puppeteers.socialnetwork.mapper

import ru.puppeteers.socialnetwork.api.dto.UserInfoResponse
import ru.puppeteers.socialnetwork.entity.User

class UserMapper {

    companion object {
        fun mapUser(user: User): UserInfoResponse {
            return UserInfoResponse(
                user.username,
                user.firstName,
                user.secondName,
                user.birthDate,
                user.city,
                user.interests,
                user.isCelebrity
            )
        }
    }
}