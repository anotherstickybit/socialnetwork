package ru.puppeteers.socialnetwork.api.endpoint

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.springframework.stereotype.Controller
import ru.puppeteers.socialnetwork.api.dto.*
import ru.puppeteers.socialnetwork.service.UserService

@Controller
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserEndpoint(
    val userService: UserService
) {

    @POST
    @Path("/login")
    fun login(loginRequest: LoginRequest): LoginResponse {
        return userService.login(loginRequest)
    }

    @POST
    @Path("/register")
    fun register(registerRequest: RegisterRequest): RegisterResponse {
        return userService.register(registerRequest)
    }

    @POST
    @Path("/info")
    fun info(infoRequest: UserInfoRequest): UserInfoResponse {
        return userService.getUserInfo(infoRequest)
    }

    @POST
    @Path("/search")
    fun search(searchRequest: UserSearchRequest): List<UserInfoResponse> {
        return userService.search(searchRequest)
    }
}