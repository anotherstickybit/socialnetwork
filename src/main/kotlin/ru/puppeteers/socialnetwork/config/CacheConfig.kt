package ru.puppeteers.socialnetwork.config


import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import ru.puppeteers.socialnetwork.entity.PostEntity

@Configuration
@EnableRedisRepositories
class CacheConfig(
    val objectMapper: ObjectMapper,
    @Value("\${spring.data.redis.host}") val redisHost: String,
    @Value("\${spring.data.redis.port}") val redisPort: Int
) {
    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val configuration = RedisStandaloneConfiguration(redisHost, redisPort)
        return JedisConnectionFactory(configuration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, PostEntity> {
        val template = RedisTemplate<String, PostEntity>()
        template.connectionFactory = jedisConnectionFactory()
        template.valueSerializer = Jackson2JsonRedisSerializer(objectMapper, PostEntity::class.java)

        return template
    }
}