package com.zacky.fundamentalsubmission.data.remote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserRoomDb : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserRoomDb? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserRoomDb {
            if (INSTANCE == null) {
                synchronized(FavoriteUserRoomDb::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUserRoomDb::class.java,
                        "favorite_user_db"
                    ).build()
                }
            }
            return INSTANCE as FavoriteUserRoomDb
        }
    }
}