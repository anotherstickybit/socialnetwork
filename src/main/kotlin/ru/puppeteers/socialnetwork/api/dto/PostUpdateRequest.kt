package ru.puppeteers.socialnetwork.api.dto

import java.util.*

data class PostUpdateRequest(
    val id: UUID,
    val newText: String
)