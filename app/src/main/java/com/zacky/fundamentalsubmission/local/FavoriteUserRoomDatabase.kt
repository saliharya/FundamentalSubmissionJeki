package com.zacky.fundamentalsubmission.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zacky.fundamentalsubmission.model.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
}