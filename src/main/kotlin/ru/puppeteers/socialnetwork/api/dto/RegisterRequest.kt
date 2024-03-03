package ru.puppeteers.socialnetwork.api.dto

import java.time.LocalDate

data class RegisterRequest(
    val email: String,
    val firstName: String,
    val secondName: String,
    val gender: Int,
    val birthDate: LocalDate,
    val city: String,
    val password: String,
    val interests: Set<String>,
    val isCelebrity: Boolean
)
