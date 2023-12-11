package com.zacky.fundamentalsubmission.repository

import com.zacky.fundamentalsubmission.local.FavoriteUserDao
import com.zacky.fundamentalsubmission.model.GithubUser

class FavoriteRepository(private val mFavoriteUserDao: FavoriteUserDao) {

    suspend fun getFavoriteUsers(): List<GithubUser> = mFavoriteUserDao.getFavoriteUsers()

    suspend fun insert(githubUser: GithubUser) {
        mFavoriteUserDao.insertFavoriteUser(githubUser)
    }

    suspend fun delete(githubUser: GithubUser) {
        mFavoriteUserDao.deleteFavoriteUser(githubUser)
    }

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRepository? = null

        fun getInstance(favoriteUserDao: FavoriteUserDao): FavoriteRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = INSTANCE ?: FavoriteRepository(favoriteUserDao)
                INSTANCE = instance
                instance
            }
        }
    }
}