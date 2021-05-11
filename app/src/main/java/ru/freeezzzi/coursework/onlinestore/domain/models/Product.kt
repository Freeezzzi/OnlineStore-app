package ru.freeezzzi.coursework.onlinestore.domain.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

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
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString().toString(),
        parcel.readSerializable() as Category,
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Product) return false
        return (other.id ?: 0).equals(id)
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeSerializable(category)
        parcel.writeString(price)
        parcel.writeLong(amount)
        parcel.writeString(imageUrl)
        parcel.writeLong(bought)
        parcel.writeString(country)
        parcel.writeString(brand)
        parcel.writeByte(if (onSale) 1 else 0)
        parcel.writeString(weight)
        parcel.writeInt(countInCart)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
