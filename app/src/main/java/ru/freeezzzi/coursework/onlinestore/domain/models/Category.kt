package ru.freeezzzi.coursework.onlinestore.domain.models

import java.io.Serializable

data class Category(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val relatedCategories: String // разделитель ;
) : Serializable
