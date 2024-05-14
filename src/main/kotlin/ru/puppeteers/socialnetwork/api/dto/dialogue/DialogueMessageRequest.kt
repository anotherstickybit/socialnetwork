package ru.puppeteers.socialnetwork.api.dto.dialogue

import java.util.*

data class DialogueMessageRequest(
    val dialogueId: UUID,
    val authorId: UUID,
    val messageText: String
)