package com.zacky.fundamentalsubmission.ui.viewmodel

import androidx.lifecycle.*
import com.zacky.fundamentalsubmission.model.FavoriteUser
import com.zacky.fundamentalsubmission.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteUserRepository: FavoriteRepository) : ViewModel() {
    private val _favoriteUsersLiveData = MutableLiveData<List<FavoriteUser>>()
    val favoriteUsersLiveData: LiveData<List<FavoriteUser>> get() = _favoriteUsersLiveData

    fun getAllFavoriteUsers() {
        viewModelScope.launch {
            _favoriteUsersLiveData.value = favoriteUserRepository.getFavoriteUsers()
        }
    }
}
