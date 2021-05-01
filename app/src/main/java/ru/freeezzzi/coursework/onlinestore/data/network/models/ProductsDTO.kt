package ru.freeezzzi.coursework.onlinestore.data.network.models

import com.squareup.moshi.JsonClass
import ru.freeezzzi.coursework.onlinestore.domain.models.Product

@JsonClass(generateAdapter = true)
data class ProductsDTO(
    val id: Long,
    val title: String,
    val category: CategoryDTO,
    val price: String,
    val amount: Long,
    val imageUrl: String,
    val bought: Long,
    val country: String,
    val brand: String,
    val onSale: Boolean,
    val weight: String
){
    fun toProduct(): Product = Product(
        id = id,
        title = title,
        category = category.toCategory(),
        price = price,
        amount = amount,
        imageUrl = imageUrl,
        bought = bought,
        country = country,
        brand = brand,
        onSale = onSale,
            weight = weight,
            countInCart = 0
    )
}
