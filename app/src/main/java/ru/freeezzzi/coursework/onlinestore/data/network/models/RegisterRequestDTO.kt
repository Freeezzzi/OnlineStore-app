package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterRequestDTO(
    val pwd: String,
    val name:String,
    val email:String,
    val phone:String
)