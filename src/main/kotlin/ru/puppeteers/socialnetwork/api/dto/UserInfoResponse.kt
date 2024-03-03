package ru.puppeteers.socialnetwork.api.dto

import java.util.*

data class UserInfoResponse(
    val username: String,
    val firstName: String,
    val secondName: String,
    val birthDate: Date,
    val city: String,
    val interests: Set<String>?,
    val isCelebrity: Boolean
)