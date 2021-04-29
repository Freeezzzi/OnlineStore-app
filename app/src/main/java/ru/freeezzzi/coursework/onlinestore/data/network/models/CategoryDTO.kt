package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryDTO(
    val id: Long,
    val title: String,
    val imageUrl: String
)
