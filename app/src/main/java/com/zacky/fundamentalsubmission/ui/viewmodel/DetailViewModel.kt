package com.zacky.fundamentalsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zacky.fundamentalsubmission.model.FavoriteUser
import com.zacky.fundamentalsubmission.repository.FavoriteRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val mFavoriteUserRepository: FavoriteRepository,
) : ViewModel() {
    private val _responseLiveData: MutableLiveData<FavoriteUser> = MutableLiveData()
    val responseLiveData: LiveData<FavoriteUser> = _responseLiveData

    private val _favoriteUsersLiveData: MutableLiveData<List<FavoriteUser>> = MutableLiveData()
    var favoriteUsersLiveData: LiveData<List<FavoriteUser>> = _favoriteUsersLiveData

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun setUser(user: FavoriteUser) {
        _responseLiveData.postValue(user)
    }

    fun getFavoriteUsers() {
        viewModelScope.launch {
            _favoriteUsersLiveData.postValue(mFavoriteUserRepository.getFavoriteUsers())
        }
    }

    fun toggleFavoriteUser() {
        viewModelScope.launch {
            responseLiveData.value?.let { user ->
                if (user.isFavorite) mFavoriteUserRepository.delete(user)
                else mFavoriteUserRepository.insert(user)

                user.isFavorite = !user.isFavorite
                _responseLiveData.postValue(user)
            }
        }
    }
}