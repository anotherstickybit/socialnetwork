package ru.puppeteers.socialnetwork.api.endpoint

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import ru.puppeteers.socialnetwork.api.dto.BaseResponse
import ru.puppeteers.socialnetwork.api.dto.FriendRequest
import ru.puppeteers.socialnetwork.entity.User
import ru.puppeteers.socialnetwork.service.FriendService

@Controller
@Path("/friend")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FriendEndpoint(
    val friendService: FriendService
) {

    @POST
    @Path("/add")
    fun add(friendRequest: FriendRequest): BaseResponse {
        val authenticatedUser = SecurityContextHolder.getContext().authentication.principal as User
        return friendService.addFriend(authenticatedUser.id, friendRequest.friendId)
    }

    @POST
    @Path("/remove")
    fun remove(friendRequest: FriendRequest): BaseResponse {
        val authenticatedUser = SecurityContextHolder.getContext().authentication.principal as User
        return friendService.removeFriend(authenticatedUser.id, friendRequest.friendId)
    }

}