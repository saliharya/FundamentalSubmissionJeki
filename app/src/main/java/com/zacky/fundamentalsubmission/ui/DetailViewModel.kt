package com.zacky.fundamentalsubmission.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.zacky.fundamentalsubmission.data.remote.database.FavoriteUser
import com.zacky.fundamentalsubmission.data.remote.repository.FavoriteRepository

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val mFavoriteUserRepository: FavoriteRepository = FavoriteRepository(getApplication())
    val favoriteUserIsExist: LiveData<Boolean> =
        mFavoriteUserRepository.getFavoriteUserByUsername(username = "")


    fun addFavoriteUser(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun checkFavoriteUserIsExist(): Boolean? {
        return favoriteUserIsExist.value
    }
}