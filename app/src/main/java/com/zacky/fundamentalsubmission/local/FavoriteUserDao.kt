package com.zacky.fundamentalsubmission.local

import androidx.room.*
import com.zacky.fundamentalsubmission.model.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM FavoriteUser")
    suspend fun getFavoriteUsers(): List<FavoriteUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: FavoriteUser)

    @Delete
    suspend fun deleteFavoriteUser(user: FavoriteUser)
}