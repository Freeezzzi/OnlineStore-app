package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass
import ru.freeezzzi.coursework.onlinestore.domain.models.User

@JsonClass(generateAdapter = true)
data class UserProfileDTO(
    val id: Long,
    val pwd: String,
    val email: String,
    val name: String,
    val phone: String,
    val balance: Long
)

fun fromUser(user: User): UserProfileDTO = UserProfileDTO(
    id = user.id,
    pwd = user.pwd,
    email = user.email,
    name = user.name,
    phone = user.phone,
    balance = user.balance
)
