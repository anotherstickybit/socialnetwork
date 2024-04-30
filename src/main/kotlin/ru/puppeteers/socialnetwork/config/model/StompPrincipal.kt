package ru.puppeteers.socialnetwork.config.model

import java.security.Principal

data class StompPrincipal(
    val username: String
) : Principal {
    override fun getName(): String {
        return username
    }
}