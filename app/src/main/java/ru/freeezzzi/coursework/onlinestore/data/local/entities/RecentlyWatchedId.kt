package ru.freeezzzi.coursework.onlinestore.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentlyWatched")
class RecentlyWatchedId(
    @PrimaryKey  @ColumnInfo(name = "time") var time: Long,

    @ColumnInfo(name = "id") var id: Long?
) {
}