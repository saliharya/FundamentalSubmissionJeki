package com.zacky.fundamentalsubmission.data.remote.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favoriteuser ORDER BY id ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>


    @Query("SELECT EXISTS(SELECT * FROM favoriteuser WHERE username = :username)")
    fun findFavoriteUserByUsername(username: String): LiveData<Boolean>
}