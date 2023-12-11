package com.zacky.fundamentalsubmission.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "image_url") var imgUrl: String?,
    @ColumnInfo(name = "username") var username: String?,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false
) : Parcelable