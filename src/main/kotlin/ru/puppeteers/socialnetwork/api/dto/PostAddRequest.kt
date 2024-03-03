package ru.puppeteers.socialnetwork.api.dto

import java.util.*

data class PostAddRequest(
    val userId: UUID,
    val text: String
)