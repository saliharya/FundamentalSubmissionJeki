package com.zacky.fundamentalsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zacky.fundamentalsubmission.model.FavoriteUser
import com.zacky.fundamentalsubmission.repository.FavoriteRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val favoriteUserRepository: FavoriteRepository) : ViewModel() {
    private val _responseLiveData = MutableLiveData<FavoriteUser>()
    val responseLiveData: LiveData<FavoriteUser> get() = _responseLiveData

    private val _favoriteUsersLiveData = MutableLiveData<List<FavoriteUser>>()
    val favoriteUsersLiveData: LiveData<List<FavoriteUser>> get() = _favoriteUsersLiveData

    private val _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> get() = _errorLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    fun setUser(user: FavoriteUser) {
        _responseLiveData.value = user
    }

    fun getFavoriteUsers() {
        viewModelScope.launch {
            _favoriteUsersLiveData.value = favoriteUserRepository.getFavoriteUsers()
        }
    }

    fun toggleFavoriteUser() {
        viewModelScope.launch {
            responseLiveData.value?.let { user ->
                if (user.isFavorite) favoriteUserRepository.delete(user)
                else favoriteUserRepository.insert(user)

                user.isFavorite = !user.isFavorite
                _responseLiveData.value = user
            }
        }
    }
}
