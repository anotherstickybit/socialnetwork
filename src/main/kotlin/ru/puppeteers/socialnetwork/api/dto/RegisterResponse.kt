package ru.puppeteers.socialnetwork.api.dto

data class RegisterResponse(
    val success: Boolean,
    val messages: List<String?>?
)
