package ru.puppeteers.socialnetwork.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.puppeteers.socialnetwork.api.dto.dialogue.DialogueMessageRequest
import ru.puppeteers.socialnetwork.api.dto.dialogue.DialogueRequest
import ru.puppeteers.socialnetwork.api.dto.dialogue.DialogueResponse
import ru.puppeteers.socialnetwork.api.dto.dialogue.ReadDialogueMessagesRequest
import ru.puppeteers.socialnetwork.dao.DialogueDao
import ru.puppeteers.socialnetwork.entity.DialogueMessageEntity

@Service
class DialogueService(
    val dialogueDao: DialogueDao,
    @Qualifier("dialoguesTemplate") val template: RestTemplate,
) {

    fun createDialog(dialogueRequest: DialogueRequest): DialogueResponse {
        val uuid = dialogueDao.create(dialogueRequest.creatorId)
        return DialogueResponse(uuid!!)
    }

    fun createDialogV2(dialogueRequest: DialogueRequest): DialogueResponse {
        return template.postForObject(DIALOGUE_BASE_URL + "new", dialogueRequest, DialogueResponse::class.java)!!
    }

    fun createMessage(dialogueMessageRequest: DialogueMessageRequest) {
        dialogueDao.createMessage(dialogueMessageRequest.dialogueId, dialogueMessageRequest.authorId,
            dialogueMessageRequest.messageText)
    }

    fun createMessageV2(dialogueMessageRequest: DialogueMessageRequest) {
        template.postForLocation(DIALOGUE_BASE_URL + "newMessage", dialogueMessageRequest)
    }

    fun getDialogueMessages(readDialogueMessagesRequest: ReadDialogueMessagesRequest): List<DialogueMessageEntity> {
        return dialogueDao.getDialogueMessages(readDialogueMessagesRequest.dialogueId)
    }

    fun deleteDialogueMessagesV2(readDialogueMessagesRequest: ReadDialogueMessagesRequest): List<DialogueMessageEntity> {
        val messageEntityList = template.postForObject(
            DIALOGUE_BASE_URL + "read", readDialogueMessagesRequest,
            DialogueMessageEntityList::class.java
        )

        return messageEntityList!!.dialogueMessages
    }

    companion object {
        const val DIALOGUE_BASE_URL = "http://localhost:8080/dialogue/"
    }

    inner class DialogueMessageEntityList(
        val dialogueMessages: List<DialogueMessageEntity>
    )
}