package ru.freeezzzi.coursework.onlinestore.domain.models

data class Address(
    var country : String,
    var state : String,
    var city: String,
    var street : String,
    var houseNum : String,
    var apartNum : String
) {
}