package edu.uw.peihsi5.lemmeshoyu.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folderTable")
data class Folder (
    @PrimaryKey val folderName: String, // no duplicates
    @ColumnInfo(name = "folderImageUrl") val folderImageUrl: String,
)
