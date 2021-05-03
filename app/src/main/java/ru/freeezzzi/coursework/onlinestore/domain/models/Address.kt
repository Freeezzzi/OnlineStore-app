package ru.freeezzzi.coursework.onlinestore.domain.models

data class Address(
    val name: String,
    val phone: String,
    val streetAndHouse: String,
    val apart: String,
    val entrance: String,
    val floor: String
) {
    override fun toString(): String {
        return "$name $phone $streetAndHouse $apart entr. $entrance floor $floor"
    }
}
