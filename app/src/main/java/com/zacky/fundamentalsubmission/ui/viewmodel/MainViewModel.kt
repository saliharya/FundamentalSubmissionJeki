package com.zacky.fundamentalsubmission.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zacky.fundamentalsubmission.model.GithubUser
import com.zacky.fundamentalsubmission.remote.response.GithubResponse
import com.zacky.fundamentalsubmission.remote.retrofit.ApiConfig
import com.zacky.fundamentalsubmission.ui.other.SettingPreferences
import com.zacky.fundamentalsubmission.util.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    private val _userProfile = MutableLiveData<List<GithubUser>>()
    val userProfile: LiveData<List<GithubUser>> get() = _userProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> get() = _snackbarText

    companion object {
        private const val TAG = "MainViewModel"
        private const val LOGIN = "zacky"
    }

    init {
        findGithubUser(LOGIN)
    }

    fun findGithubUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUser(login)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>, response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userProfile.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _snackbarText.value = Event("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _snackbarText.value = Event("Failure: ${t.message}")
            }
        })
    }

    fun getThemeSettings(): LiveData<Boolean> = pref.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}
