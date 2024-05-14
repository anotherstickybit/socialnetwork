package ru.puppeteers.socialnetwork.service

import org.springframework.stereotype.Service
import ru.puppeteers.socialnetwork.api.dto.dialogue.DialogueMessageRequest
import ru.puppeteers.socialnetwork.api.dto.dialogue.DialogueRequest
import ru.puppeteers.socialnetwork.api.dto.dialogue.DialogueResponse
import ru.puppeteers.socialnetwork.api.dto.dialogue.ReadDialogueMessagesRequest
import ru.puppeteers.socialnetwork.dao.DialogueDao
import ru.puppeteers.socialnetwork.entity.DialogueMessageEntity

@Service
class DialogueService(
    val dialogueDao: DialogueDao
) {

    fun createDialog(dialogueRequest: DialogueRequest): DialogueResponse {
        val uuid = dialogueDao.create(dialogueRequest.creatorId)
        return DialogueResponse(uuid!!)
    }

    fun createMessage(dialogueMessageRequest: DialogueMessageRequest) {
        dialogueDao.createMessage(dialogueMessageRequest.dialogueId, dialogueMessageRequest.authorId,
            dialogueMessageRequest.messageText)
    }

    fun getDialogueMessages(readDialogueMessagesRequest: ReadDialogueMessagesRequest): List<DialogueMessageEntity> {
        return dialogueDao.getDialogueMessages(readDialogueMessagesRequest.dialogueId)
    }
}