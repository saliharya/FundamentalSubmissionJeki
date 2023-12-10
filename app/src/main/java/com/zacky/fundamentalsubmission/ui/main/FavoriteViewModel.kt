package com.zacky.fundamentalsubmission.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zacky.fundamentalsubmission.data.remote.database.FavoriteUser
import com.zacky.fundamentalsubmission.data.remote.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository : FavoriteRepository = FavoriteRepository(application)

    val favoriteUserList : LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUsers()
}