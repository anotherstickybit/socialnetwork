package ru.puppeteers.socialnetwork.config

import org.springframework.http.server.ServerHttpRequest
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import ru.puppeteers.socialnetwork.config.model.StompPrincipal
import java.security.Principal

class CustomHandshakeHandler : DefaultHandshakeHandler() {

    override fun determineUser(
        request: ServerHttpRequest,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Principal? {
        return request.principal?.let { StompPrincipal(it.name) }
    }
}