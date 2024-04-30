package ru.puppeteers.socialnetwork.config

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration
import ru.puppeteers.socialnetwork.api.endpoint.*

@Configuration
class JerseyConfig : ResourceConfig() {
    init {
        register(UserEndpoint::class.java)
        register(FriendEndpoint::class.java)
        register(PostEndpoint::class.java)
        register(FeedEndpoint::class.java)
        register(WsPostChannel::class.java)
    }
}