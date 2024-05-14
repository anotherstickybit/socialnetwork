package ru.puppeteers.socialnetwork.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.puppeteers.socialnetwork.entity.DialogueMessageEntity
import ru.puppeteers.socialnetwork.exception.DialogueCreationException
import ru.puppeteers.socialnetwork.exception.DialogueMessageCreationException
import ru.puppeteers.socialnetwork.exception.DialogueMessageReadException
import java.util.*

@Service
class DialogueDao(
    val template: NamedParameterJdbcTemplate
) {

    @Transactional
    fun create(creatorId: UUID): UUID? {
        try {
            return template.queryForObject("insert into dialogue(creator_id) values (:creator_id) returning id",
                mapOf("creator_id" to creatorId),
                UUID::class.java
            )
        } catch (e: Exception) {
            throw DialogueCreationException(e.message)
        }
    }

    @Transactional
    fun createMessage(dialogueId: UUID, authorId: UUID, message: String) {
        try {
            template.update("insert into dialogue_message(dialogue_id, author_id, message_text) values " +
                    "(:dialogue_id, :author_id, :message_text)",
                mapOf(
                    "dialogue_id" to dialogueId,
                    "author_id" to authorId,
                    "message_text" to message
                )
            )
        } catch (e: Exception) {
            throw DialogueMessageCreationException(e.message)
        }
    }

    @Transactional(readOnly = true)
    fun getDialogueMessages(dialogueId: UUID): List<DialogueMessageEntity> {
        try {
            return template.query("select id, dialogue_id, author_id, message_text, created_date " +
                    "from dialogue_message where dialogue_id = :dialogue_id",
                mapOf("dialogue_id" to dialogueId)
            ) { rs, _ ->
                DialogueMessageEntity(
                    rs.getObject("id") as UUID,
                    rs.getObject("dialogue_id") as UUID,
                    rs.getObject("author_id") as UUID,
                    rs.getString("messageText"),
                    rs.getTimestamp("created_date").toLocalDateTime(),

                )
            }
        } catch (e: Exception) {
            throw DialogueMessageReadException(e.message)
        }
    }
}