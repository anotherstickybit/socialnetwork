package ru.puppeteers.socialnetwork.entity

import java.time.LocalDateTime
import java.util.*

data class DialogueMessageEntity(
    val id: UUID,
    val dialogueId: UUID,
    val authorId: UUID,
    val messageText: String,
    val creationDate: LocalDateTime
)