package ru.puppeteers.socialnetwork.api.dto

data class LoginRequest(
    val email: String,
    val password: String
)