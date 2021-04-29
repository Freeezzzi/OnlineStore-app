package ru.freeezzzi.coursework.onlinestore.domain.models

data class User(
    var pwd: String, //6-25
    var name: String, //not empty 25
    var phone: String, // 18
    var email: String, // 40
    val token: String,
    var balance: Long,
    var address: Address? = null
)
