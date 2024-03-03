package ru.puppeteers.socialnetwork.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import ru.puppeteers.socialnetwork.dao.UserDao

@Configuration
class AppConfig(
    val userDao: UserDao
) {

    @Bean
    fun objectMapper(): ObjectMapper {
        return JsonMapper.builder()
            .addModule(JavaTimeModule())
            .addModule(KotlinModule.Builder().build())
            .build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->  userDao.getUserByEmail(username)
            ?: throw UsernameNotFoundException("User with username: $username not found.") }
    }
}