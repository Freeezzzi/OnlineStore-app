package ru.freeezzzi.coursework.onlinestore.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.freeezzzi.coursework.onlinestore.data.local.dao.ProductsDao
import ru.freeezzzi.coursework.onlinestore.data.local.entities.CartProductId
import ru.freeezzzi.coursework.onlinestore.data.local.entities.RecentlyWatchedId

@Database(entities = [CartProductId::class, RecentlyWatchedId::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductsDao

    companion object {
        private const val DATABASE_NAME = "products_database"

        @Volatile
        private var instance: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase =
            instance ?: synchronized(this) { instance ?: build(context) }

        private fun build(context: Context): LocalDatabase =
            Room.databaseBuilder(context, LocalDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
