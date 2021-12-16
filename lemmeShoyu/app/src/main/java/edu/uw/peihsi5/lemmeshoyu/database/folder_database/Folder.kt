/**
 * Pacy Wu: I wrote the data class Folder, which stores the folder information, including
 * folder name and folder image url.
 **/

package edu.uw.peihsi5.lemmeshoyu.database

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folderTable")
data class Folder (
    @PrimaryKey val folderName: String, // no duplicates
    @ColumnInfo(name = "folderImageUrl", defaultValue = "NULL") @Nullable val folderImageUrl: String?,
)
