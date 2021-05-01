package ru.freeezzzi.coursework.onlinestore.domain.models

data class Product(
    val id: Long?,
    val title: String,
    val category: Category,
    val price: String,
    val amount: Long,
    val imageUrl: String,
    val bought: Long,
    val country: String,
    val brand: String,
    val onSale: Boolean,
    val weight: String,
    // only local field
    var countInCart: Int
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Product) return false
        return (other.id ?: 0).equals(id)
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
