package ru.puppeteers.socialnetwork.config

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration
import ru.puppeteers.socialnetwork.api.endpoint.UserEndpoint

@Configuration
class JerseyConfig : ResourceConfig() {
    init {
        register(UserEndpoint::class.java)
    }
}