package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequestDTO(
    val username:String,
    val pwd:String
)
