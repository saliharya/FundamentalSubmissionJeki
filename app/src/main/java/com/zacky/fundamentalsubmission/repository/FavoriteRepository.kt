package com.zacky.fundamentalsubmission.repository

import com.zacky.fundamentalsubmission.local.FavoriteUserDao
import com.zacky.fundamentalsubmission.model.FavoriteUser

class FavoriteRepository(private val mFavoriteUserDao: FavoriteUserDao) {

    suspend fun getFavoriteUsers(): List<FavoriteUser> = mFavoriteUserDao.getAllFavoriteUsers()

    suspend fun insert(githubUser: FavoriteUser) {
        mFavoriteUserDao.insert(githubUser)
    }

    suspend fun delete(githubUser: FavoriteUser) {
        mFavoriteUserDao.delete(githubUser)
    }
}