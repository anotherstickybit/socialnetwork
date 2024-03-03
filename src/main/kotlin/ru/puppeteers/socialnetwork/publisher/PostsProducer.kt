package ru.puppeteers.socialnetwork.publisher

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.puppeteers.socialnetwork.entity.PostEntity


@Service
class PostsProducer(
    @Value("\${rabbit.exchange.name}") val exchangeName: String,
    @Value("\${rabbit.routing.key}") val routingKey: String,
    val template: RabbitTemplate
) {

    fun sendPostCreated(postEntity: PostEntity) {
        template.convertAndSend(exchangeName, routingKey, postEntity)
    }
}