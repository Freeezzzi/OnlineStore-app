package ru.freeezzzi.coursework.onlinestore.domain.models

import java.io.Serializable

data class Recipe(
    val id: Long = 0,
    val name: String = "",
    val difficulty: Int = DIFFICULTY_EASY,
    val cookingTime: Int = 1, // 1 minute
    val imageUrl: String = "",
    val ingredientNames: ArrayList<String> = ArrayList(),
    val ingredientCount: ArrayList<String> = ArrayList(),
    var ingredientProducts: ArrayList<Product> = ArrayList(),
    val stepsInfo: ArrayList<String> = ArrayList(),
    val stepsImages: ArrayList<String> = ArrayList(),
) : Serializable {
    companion object {
        const val DIFFICULTY_ALL = -1
        const val DIFFICULTY_EASY = 0
        const val DIFFICULTY_MEDIUM = 1
        const val DIFFUCULTY_HARD = 2
    }
}
