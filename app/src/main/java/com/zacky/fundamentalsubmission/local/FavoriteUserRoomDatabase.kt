package com.zacky.fundamentalsubmission.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zacky.fundamentalsubmission.model.GithubUser

@Database(entities = [GithubUser::class], version = 1)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {

        @Volatile
        private var instance: FavoriteUserRoomDatabase? = null

        fun getInstance(context: Context): FavoriteUserRoomDatabase {
            return synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserRoomDatabase::class.java,
                    "favorite.db"
                ).build().also { instance = it }
            }
        }

    }
}