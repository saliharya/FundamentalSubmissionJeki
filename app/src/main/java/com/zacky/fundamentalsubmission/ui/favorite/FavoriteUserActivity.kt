package com.zacky.fundamentalsubmission.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zacky.fundamentalsubmission.databinding.ActivityFavoriteUserBinding
import com.zacky.fundamentalsubmission.ui.main.FavoriteViewModel

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter = FavoriteAdapter(this)

        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUser.adapter = adapter

        viewModel.favoriteUserList.observe(this) { favoriteUsers ->
            adapter.submitList(favoriteUsers)
            checkIfListIsEmpty(favoriteUsers.isEmpty())
        }
    }

    private fun checkIfListIsEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            binding.tvNoFavoriteUser.visibility = View.VISIBLE
        } else {
            binding.tvNoFavoriteUser.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_FAVORITE = "extra-favorite"
    }
}
