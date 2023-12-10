package com.zacky.fundamentalsubmission.ui.di

import android.app.Application
import android.content.Context
import com.zacky.fundamentalsubmission.data.remote.database.FavoriteUser
import com.zacky.fundamentalsubmission.data.remote.database.FavoriteUserRoomDb
import com.zacky.fundamentalsubmission.data.remote.repository.FavoriteRepository
import com.zacky.fundamentalsubmission.data.remote.retrofit.ApiConfig
import com.zacky.fundamentalsubmission.util.AppExecutors

//object Injection {
//    fun provideRepository(context: Context): FavoriteRepository {
//        val apiService = ApiConfig.getApiService()
//        val database = FavoriteUserRoomDb.getInstance(context)
//        val dao = database.FavoriteUserDao()
//        val appExecutors = AppExecutors()
//        return FavoriteRepository(Application())
//    }
//}