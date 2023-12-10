package com.zacky.fundamentalsubmission.data.remote.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.zacky.fundamentalsubmission.data.remote.database.FavoriteUser
import com.zacky.fundamentalsubmission.data.remote.database.FavoriteUserDao
import com.zacky.fundamentalsubmission.data.remote.database.FavoriteUserRoomDb
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDb.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUsers()

    fun insert(note: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(note) }
    }

    fun delete(note: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(note) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<Boolean> =
        mFavoriteUserDao.findFavoriteUserByUsername(username)

}