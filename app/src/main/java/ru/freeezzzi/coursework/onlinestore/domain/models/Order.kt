package ru.freeezzzi.coursework.onlinestore.domain.models

import android.os.Parcel
import android.os.Parcelable

data class Order(
    val id: Long,
    val user_id: Long,
    val status: Int,
    val products: ArrayList<Product>, // В продукте в поле itemCountInCart хранится кол-во предметов в заказе
    val address: String,
    val orderTime: String,
    val deliveryTime: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        {
            val list = ArrayList<Product>()
            parcel.readList(list, Product.CREATOR.javaClass.classLoader)
            list
        }(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(user_id)
        parcel.writeInt(status)
        parcel.writeList(products)
        parcel.writeString(address)
        parcel.writeString(orderTime)
        parcel.writeString(deliveryTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        const val STATUS_PLACED = 0
        const val STATUS_PREPARING = 1
        const val STATUS_ONTHEWAY = 2
        const val STATUS_DELIVERED = 3
        const val ORDERS_ALL = 4 // только для передачи в качестве параметра для фильтра

        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}
