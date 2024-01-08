package ru.puppeteers.socialnetwork.config

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
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->  userDao.getUserByEmail(username)
            ?: throw UsernameNotFoundException("User with username: $username not found.") }
    }
}