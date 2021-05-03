package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfileDTO(
    val id: Long,
    val pwd: String,
    val email: String,
    val name: String,
    val phone: String,
    val balance: Long
)
