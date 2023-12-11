package com.zacky.fundamentalsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zacky.fundamentalsubmission.model.FavoriteUser
import com.zacky.fundamentalsubmission.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val mFavoriteUserRepository: FavoriteRepository) : ViewModel() {
    private val _favoriteUsersLiveData: MutableLiveData<List<FavoriteUser>> = MutableLiveData()
    var favoriteUsersLiveData: LiveData<List<FavoriteUser>> = _favoriteUsersLiveData

    fun getAllFavoriteUsers() {
        viewModelScope.launch {
            _favoriteUsersLiveData.postValue(mFavoriteUserRepository.getFavoriteUsers())
        }
    }
}