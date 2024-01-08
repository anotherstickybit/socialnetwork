package ru.puppeteers.socialnetwork.api.dto

import java.time.LocalDate

data class RegisterDto(
    val firstName: String,
    val secondName: String,
    val birthDate: LocalDate,
    val city: String,
    val password: String
)
