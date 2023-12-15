package com.zacky.fundamentalsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zacky.fundamentalsubmission.model.GithubUser
import com.zacky.fundamentalsubmission.repository.FavoriteRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val favoriteUserRepository: FavoriteRepository) : ViewModel() {
    private val _responseLiveData = MutableLiveData<GithubUser>()
    val responseLiveData: LiveData<GithubUser> get() = _responseLiveData

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


    suspend fun checkIfFavorite(user: GithubUser) {
        val favoriteUsers = favoriteUserRepository.getFavoriteUsers()
        val isFavorite = favoriteUsers.any { it.id == user.id }
        user.isFavorite = isFavorite
        _responseLiveData.value = user
    }
}
