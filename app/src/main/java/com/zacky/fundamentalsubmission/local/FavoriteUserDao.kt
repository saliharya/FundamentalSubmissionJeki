package com.zacky.fundamentalsubmission.local

import androidx.room.*
import com.zacky.fundamentalsubmission.model.GithubUser

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM GithubUser")
    fun getFavoriteUsers(): List<GithubUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: GithubUser)

    @Delete
    suspend fun deleteFavoriteUser(user: GithubUser)
}