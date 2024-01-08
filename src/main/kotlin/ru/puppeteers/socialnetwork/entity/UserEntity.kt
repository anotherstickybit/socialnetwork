package ru.puppeteers.socialnetwork.entity

import java.util.UUID

data class User(
    val id: UUID,
    val enabled: Boolean,
    val password: String
)
