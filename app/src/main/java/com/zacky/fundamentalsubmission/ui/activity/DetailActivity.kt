package com.zacky.fundamentalsubmission.ui.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zacky.fundamentalsubmission.R
import com.zacky.fundamentalsubmission.databinding.ActivityDetailBinding
import com.zacky.fundamentalsubmission.local.FavoriteUserRoomDatabase
import com.zacky.fundamentalsubmission.remote.response.DetailUserResponse
import com.zacky.fundamentalsubmission.remote.retrofit.ApiConfig
import com.zacky.fundamentalsubmission.repository.FavoriteRepository
import com.zacky.fundamentalsubmission.ui.adapter.SectionsPagerAdapter
import com.zacky.fundamentalsubmission.ui.viewmodel.DetailViewModel
import com.zacky.fundamentalsubmission.ui.viewmodel.factory.DetailViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var favoriteRepository: FavoriteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(LOGIN_NAME)
        if (username != null) {
            findDetailUser(username)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if (username != null) {
            sectionsPagerAdapter.username = username
        }

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabFoll

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
        val db = Room.databaseBuilder(
            applicationContext, FavoriteUserRoomDatabase::class.java, "favorite_user_db"
        ).build()

        val favoriteUserDao = db.favoriteUserDao()
        favoriteRepository = FavoriteRepository.getInstance(favoriteUserDao)
        viewModel = ViewModelProvider(
            this, DetailViewModelFactory(favoriteRepository)
        )[DetailViewModel::class.java]
        binding.btnFavorite.setOnClickListener {
            viewModel.toggleFavoriteUser()
        }

        viewModel.responseLiveData.observe(this) { user ->
            if (user.isFavorite) {
                val color = ContextCompat.getColor(
                    this@DetailActivity, R.color.red
                )
                binding.btnFavorite.imageTintList = ColorStateList.valueOf(color)
            } else {
                val color = ContextCompat.getColor(
                    this@DetailActivity, R.color.black
                )
                binding.btnFavorite.imageTintList = ColorStateList.valueOf(color)
            }
        }
    }

    private fun setReviewData(response: DetailUserResponse) {
        binding.detailUserName.text = response.name
        Glide.with(this@DetailActivity).load(response.avatarUrl).circleCrop()
            .into(binding.imgDetailUser)

        binding.detailName.text = response.login

        val followersCount = response.followers
        val followingCount = response.following

        binding.tvFollower.text = resources.getString(R.string.followers, followersCount)
        binding.tvFollowing.text = resources.getString(R.string.following, followingCount)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun findDetailUser(username: String) {
        showLoading(true)

        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>, response: Response<DetailUserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setReviewData(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val LOGIN_NAME = "dataUser"
        const val TAG = "DetailActivity"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following,
        )
    }
}
