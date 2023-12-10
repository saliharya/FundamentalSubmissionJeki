package com.zacky.fundamentalsubmission.repository

import com.zacky.fundamentalsubmission.local.FavoriteUserDao
import com.zacky.fundamentalsubmission.model.FavoriteUser

class FavoriteRepository(private val mFavoriteUserDao: FavoriteUserDao) {

    suspend fun getFavoriteUsers(): List<FavoriteUser> = mFavoriteUserDao.getFavoriteUsers()

    suspend fun insert(githubUser: FavoriteUser) {
        mFavoriteUserDao.insertFavoriteUser(githubUser)
    }

    suspend fun delete(githubUser: FavoriteUser) {
        mFavoriteUserDao.deleteFavoriteUser(githubUser)
    }
}