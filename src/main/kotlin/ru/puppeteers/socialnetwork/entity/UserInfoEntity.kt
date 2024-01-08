package ru.puppeteers.socialnetwork.entity

import java.time.LocalDate
import java.util.*

data class UserInfo(
    val id: UUID,
    val firstName: String,
    val secondName: String,
    val birthDate: LocalDate,
    val city: String
)
