package com.zacky.fundamentalsubmission.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zacky.fundamentalsubmission.model.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favoriteuser ORDER BY id ASC")
    fun getAllFavoriteUsers(): List<FavoriteUser>


    @Query("SELECT EXISTS(SELECT * FROM favoriteuser WHERE username = :username)")
    fun findFavoriteUserByUsername(username: String): LiveData<Boolean>
}