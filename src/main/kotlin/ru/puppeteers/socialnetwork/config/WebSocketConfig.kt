package ru.puppeteers.socialnetwork.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    @Value("\${spring.rabbitmq.host}") val rabbitHost: String,
    @Value("\${spring.rabbitmq.port}") val rabbitPort: Int,
    @Value("\${spring.rabbitmq.username}") val rabbitUsername: String,
    @Value("\${spring.rabbitmq.password}") val rabbitPass: String,
    @Value("\${stomp.broker.relay}") val stompBrokerRelay: String,
) : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
//        registry.setApplicationDestinationPrefixes("/app", "/post")
//            .enableStompBrokerRelay(stompBrokerRelay)
//            .setRelayHost(rabbitHost)
//            .setRelayPort(rabbitPort)
//            .setSystemLogin(rabbitUsername)
//            .setSystemPasscode(rabbitPass)
//            .setClientLogin(rabbitUsername)
//            .setClientPasscode(rabbitPass)

        registry.enableSimpleBroker("/user")
        registry.setApplicationDestinationPrefixes("/app")
        registry.setUserDestinationPrefix("/user")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOrigins("*")
            .setHandshakeHandler(CustomHandshakeHandler())
            .withSockJS()
    }
}