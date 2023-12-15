package com.zacky.fundamentalsubmission.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zacky.fundamentalsubmission.databinding.ActivityMainBinding
import com.zacky.fundamentalsubmission.local.FavoriteUserRoomDatabase
import com.zacky.fundamentalsubmission.model.GithubUser
import com.zacky.fundamentalsubmission.repository.FavoriteRepository
import com.zacky.fundamentalsubmission.ui.adapter.FavoriteAdapter
import com.zacky.fundamentalsubmission.ui.viewmodel.FavoriteViewModel
import com.zacky.fundamentalsubmission.ui.viewmodel.factory.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViews()
        initializeViewModel()
        setupRecyclerView()
    }

    private fun initializeViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    private fun initializeViewModel() {
        val favoriteUserDao = FavoriteUserRoomDatabase.getInstance(this).favoriteUserDao()


        mainViewModel = ViewModelProvider(
            this, FavoriteViewModelFactory(FavoriteRepository.getInstance(favoriteUserDao))
        )[FavoriteViewModel::class.java]
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvListUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListUser.addItemDecoration(itemDecoration)

        val emptyList: List<GithubUser> = emptyList()
        val adapter = FavoriteAdapter(emptyList)
        binding.rvListUser.adapter = adapter

        mainViewModel.favoriteUsers.observe(this) { users ->
            adapter.submitList(users)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
