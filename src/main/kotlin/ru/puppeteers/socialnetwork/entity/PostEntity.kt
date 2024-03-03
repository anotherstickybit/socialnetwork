package ru.puppeteers.socialnetwork.entity

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class PostEntity(
    val id: UUID,
    val userId: UUID,
    val text: String,
    val creationDate: LocalDateTime,
    val updatedDate: LocalDateTime
) : Serializable