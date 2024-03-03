package ru.puppeteers.socialnetwork.api.endpoint

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.springframework.stereotype.Controller
import ru.puppeteers.socialnetwork.api.dto.BaseResponse
import ru.puppeteers.socialnetwork.api.dto.PostAddRequest
import ru.puppeteers.socialnetwork.api.dto.PostRemoveRequest
import ru.puppeteers.socialnetwork.api.dto.PostUpdateRequest
import ru.puppeteers.socialnetwork.service.PostService

@Controller
@Path("/post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PostEndpoint(
    val postService: PostService
) {

    @POST
    @Path("/create")
    fun add(postAddRequest: PostAddRequest): BaseResponse {
        return postService.addPost(postAddRequest)
    }

    @POST
    @Path("/update")
    fun update(postUpdateRequest: PostUpdateRequest): BaseResponse {
        return postService.updatePost(postUpdateRequest)
    }

    @POST
    @Path("/remove")
    fun remove(postRemoveRequest: PostRemoveRequest): BaseResponse {
        return postService.removePost(postRemoveRequest)
    }
}