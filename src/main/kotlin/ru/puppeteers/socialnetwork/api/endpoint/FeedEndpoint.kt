package ru.puppeteers.socialnetwork.api.endpoint

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import ru.puppeteers.socialnetwork.api.dto.GetFeedRequest
import ru.puppeteers.socialnetwork.entity.PostEntity
import ru.puppeteers.socialnetwork.entity.User
import ru.puppeteers.socialnetwork.service.PostService

@Controller
@Path("/feed")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FeedEndpoint(
    val postService: PostService
) {

    @POST
    @Path("/get")
    fun getFeed(getFeedRequest: GetFeedRequest): List<PostEntity> {
        val authenticatedUser = SecurityContextHolder.getContext().authentication.principal as User
        return postService.getFeed(authenticatedUser.id, getFeedRequest)
    }
}