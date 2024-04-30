package ru.puppeteers.socialnetwork.api.endpoint

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class WsPostChannel(

) {

    @SubscribeMapping("/feed/posted")
    fun subscribe(stompHeaderAccessor: StompHeaderAccessor) {
        println(stompHeaderAccessor.user)
    }

}