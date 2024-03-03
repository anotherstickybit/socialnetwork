package ru.puppeteers.socialnetwork.config

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig(
    @Value("\${rabbit.queue.name}") val queueName: String,
    @Value("\${rabbit.exchange.name}") val exchangeName: String,
    @Value("\${rabbit.routing.key}") val routingKey: String
) {

    @Bean
    fun queue(): Queue {
        return Queue(queueName)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(exchangeName)
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder
            .bind(queue())
            .to(exchange())
            .with(routingKey)
    }

    @Bean
    fun converter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun amqTemplate(connectionFactory: ConnectionFactory): AmqpTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = converter()
        return rabbitTemplate
    }
}