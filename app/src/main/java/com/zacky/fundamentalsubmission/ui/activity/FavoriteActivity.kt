package com.zacky.fundamentalsubmission.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zacky.fundamentalsubmission.databinding.ActivityFavoriteBinding
import com.zacky.fundamentalsubmission.local.FavoriteUserRoomDatabase
import com.zacky.fundamentalsubmission.model.FavoriteUser
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

        initializeViewModel()
        setupRecyclerView()

        observeFavoriteUsers()
        viewModel.getAllFavoriteUsers()
    }

    private fun initializeViewModel() {
        val db = Room.databaseBuilder(
            applicationContext, FavoriteUserRoomDatabase::class.java, "favorite_user_db"
        ).build()

        val favoriteUserDao = db.favoriteUserDao()
        favoriteRepository = FavoriteRepository.getInstance(favoriteUserDao)
        viewModel = ViewModelProvider(
            this, FavoriteViewModelFactory(favoriteRepository)
        )[FavoriteViewModel::class.java]
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteUser.setHasFixedSize(true)
        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(this)
    }

    private fun observeFavoriteUsers() {
        viewModel.favoriteUsersLiveData.observe(this) { favoriteList ->
            val noDataTextView = binding.tvNoFavoriteUser
            if (favoriteList.isNullOrEmpty()) {
                noDataTextView.visibility = View.VISIBLE
                binding.rvFavoriteUser.visibility = View.GONE
            } else {
                noDataTextView.visibility = View.GONE
                binding.rvFavoriteUser.visibility = View.VISIBLE

                val adapter = FavoriteAdapter { user ->
                    navigateToDetail(user)
                }

                adapter.submitList(favoriteList)
                binding.rvFavoriteUser.adapter = adapter
            }
        }
    }

    private fun navigateToDetail(user: FavoriteUser) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }
}
