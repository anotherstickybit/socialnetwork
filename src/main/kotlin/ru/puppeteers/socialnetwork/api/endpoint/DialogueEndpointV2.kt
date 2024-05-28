package ru.puppeteers.socialnetwork.api.endpoint

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.springframework.stereotype.Controller
import ru.puppeteers.socialnetwork.api.dto.dialogue.DialogueMessageRequest
import ru.puppeteers.socialnetwork.api.dto.dialogue.DialogueRequest
import ru.puppeteers.socialnetwork.api.dto.dialogue.DialogueResponse
import ru.puppeteers.socialnetwork.api.dto.dialogue.ReadDialogueMessagesRequest
import ru.puppeteers.socialnetwork.entity.DialogueMessageEntity
import ru.puppeteers.socialnetwork.service.DialogueService

@Controller
@Path("/dialogueV2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class DialogueEndpointV2(
    val dialogueService: DialogueService
) {

    @POST
    @Path("/new")
    fun createDialogue(dialogueRequest: DialogueRequest): DialogueResponse {
        return dialogueService.createDialogV2(dialogueRequest)
    }

    @POST
    @Path("/newMessage")
    fun createNewDialogueMessage(dialogueMessageRequest: DialogueMessageRequest) {
        dialogueService.createMessageV2(dialogueMessageRequest)
    }

    @POST
    @Path("/read")
    fun readDialogueMessages(readDialogueMessagesRequest: ReadDialogueMessagesRequest): List<DialogueMessageEntity> {
        return dialogueService.getDialogueMessagesV2(readDialogueMessagesRequest)
    }
}