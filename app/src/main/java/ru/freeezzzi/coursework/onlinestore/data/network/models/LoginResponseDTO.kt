package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass
import ru.freeezzzi.coursework.onlinestore.domain.models.User

@JsonClass(generateAdapter = true)
data class LoginResponseDTO(
    val userProfile: UserProfileDTO,
    val token: String,
)

fun LoginResponseDTO.toUser(): User =
    User(
        name = userProfile.name,
        pwd = userProfile.pwd,
        email = userProfile.email,
        phone = userProfile.phone,
        balance = userProfile.balance,
        token = token
    )
