package com.zacky.fundamentalsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zacky.fundamentalsubmission.model.GithubUser
import com.zacky.fundamentalsubmission.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    private val _favoriteUsers = MutableLiveData<List<GithubUser>>()
    val favoriteUsers: LiveData<List<GithubUser>> get() = _favoriteUsers

    init {
        fetchFavoriteUsers()
    }

    private fun fetchFavoriteUsers() {
        viewModelScope.launch {
            val users = favoriteRepository.getFavoriteUsers()
            _favoriteUsers.postValue(users)
        }
    }
}
