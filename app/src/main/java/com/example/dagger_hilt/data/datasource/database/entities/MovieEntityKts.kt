package com.example.dagger_hilt.data.datasource.database.entities

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "movie")
class MovieEntityKts(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "original_title") var originalTitle: String,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "poster_path") var posterPath: String,
    @ColumnInfo(name = "like") var like: Boolean
)