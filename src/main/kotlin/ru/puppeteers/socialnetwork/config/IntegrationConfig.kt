package ru.puppeteers.socialnetwork.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class IntegrationConfig {

    @Bean("dialoguesTemplate")
    fun dialoguesTemplate(): RestTemplate {
        return RestTemplate()
    }
}