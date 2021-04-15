package ru.freeezzzi.coursework.onlinestore.domain.models

data class User(
    val username: String,
    val token: String,
    var name: String
    //var address: Address?
)
