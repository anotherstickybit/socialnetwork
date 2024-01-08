package ru.puppeteers.socialnetwork.entity

import java.time.LocalDate
import java.util.*

data class UserInfoEntity(
    val id: UUID,
    val email: String,
    val firstName: String,
    val secondName: String,
    val birthDate: LocalDate,
    val city: String
)
