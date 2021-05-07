package ru.freeezzzi.coursework.onlinestore.data.local.dao

import androidx.room.*
import ru.freeezzzi.coursework.onlinestore.data.local.entities.CartProductId
import ru.freeezzzi.coursework.onlinestore.data.local.entities.RecentlyWatchedId

@Dao
interface ProductsDao {
    @Query("SELECT * FROM cart")
    suspend fun getCart(): List<CartProductId>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartProductId: CartProductId): Unit

    @Delete
    suspend fun delete(cartProductId: CartProductId): Unit

    @Query("SELECT * FROM recentlyWatched")
    suspend fun getRecentlyWatched(): List<RecentlyWatchedId>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recentlyWatchedId: RecentlyWatchedId): Unit

    @Delete
    suspend fun delete(recentlyWatchedId: RecentlyWatchedId): Unit

    @Query("DELETE FROM recentlyWatched WHERE id = :recentlyWatchedId")
    suspend fun deletePrevious(recentlyWatchedId: Long): Unit

    @Query("DELETE FROM  cart")
    suspend fun deleteCartTable()

    @Query("DELETE FROM recentlyWatched")
    suspend fun deleteRecentlyWatchedTable()
}
