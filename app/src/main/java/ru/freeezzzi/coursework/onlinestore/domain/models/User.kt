package ru.freeezzzi.coursework.onlinestore.domain.models

data class User(
    val id: Long,
    var pwd: String,
    var name: String,
    var phone: String,
    var email: String,
    val token: String,
    var balance: Long,
    var address: Address?
)
