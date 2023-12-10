package com.zacky.fundamentalsubmission.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") var id: Int = 0,

    @ColumnInfo(name = "image_url") var imgUrl: String? = null,

    @ColumnInfo(name = "username") var username: String? = null,

    @ColumnInfo(name = "type") var type: String? = null
) : Parcelable