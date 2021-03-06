package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass
import ru.freeezzzi.coursework.onlinestore.domain.models.Category

@JsonClass(generateAdapter = true)
data class CategoryDTO(
    val id: Long,
    val title: String,
    val imageUrl: String
){
    fun toCategory(): Category = Category(
        id = id,
        title = title,
        imageUrl = imageUrl
    )
}
