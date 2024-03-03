package ru.puppeteers.socialnetwork.api.dto


data class BaseResponse(
    val success: Boolean,
    val messages: List<String?>?
)