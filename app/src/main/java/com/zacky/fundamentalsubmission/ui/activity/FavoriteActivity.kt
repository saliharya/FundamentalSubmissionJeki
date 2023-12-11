package com.zacky.fundamentalsubmission.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.zacky.fundamentalsubmission.databinding.ActivityFavoriteBinding
import com.zacky.fundamentalsubmission.local.FavoriteUserRoomDatabase
import com.zacky.fundamentalsubmission.repository.FavoriteRepository
import com.zacky.fundamentalsubmission.ui.adapter.FavoriteAdapter
import com.zacky.fundamentalsubmission.ui.viewmodel.FavoriteViewModel
import com.zacky.fundamentalsubmission.ui.viewmodel.factory.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteRepository: FavoriteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            applicationContext, FavoriteUserRoomDatabase::class.java, "favorite_user_db"
        ).build()

        val favoriteUserDao = db.favoriteUserDao()
        favoriteRepository = FavoriteRepository.getInstance(favoriteUserDao)
        viewModel = ViewModelProvider(
            this, FavoriteViewModelFactory(favoriteRepository)
        )[FavoriteViewModel::class.java]

        binding.rvFavoriteUser.setHasFixedSize(true)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.favoriteUsersLiveData.observe(this) { favoriteList ->
            val noDataTextView = binding.tvNoFavoriteUser

            favoriteList?.let {
                if (it.isEmpty()) {
                    noDataTextView.visibility = View.VISIBLE
                    binding.rvFavoriteUser.visibility = View.GONE
                } else {
                    noDataTextView.visibility = View.GONE
                    binding.rvFavoriteUser.visibility = View.VISIBLE
                }

                val adapter = FavoriteAdapter { user ->
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                binding.rvFavoriteUser.adapter = adapter
            }
        }
        viewModel.getAllFavoriteUsers()
    }
}