package ru.puppeteers.socialnetwork.endpoint

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserEndpoint(

) {

    @POST
    @Path("/login")
    fun login(): Response {

    }

    @POST
    @Path("/register")
    fun register(): Response {

    }
}