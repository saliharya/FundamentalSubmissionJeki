package com.zacky.fundamentalsubmission.ui.activity

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.zacky.fundamentalsubmission.R
import com.zacky.fundamentalsubmission.model.ItemsItem
import com.zacky.fundamentalsubmission.databinding.ActivityMainBinding
import com.zacky.fundamentalsubmission.ui.SettingPreferences
import com.zacky.fundamentalsubmission.ui.adapter.UserAdapter
import com.zacky.fundamentalsubmission.ui.viewmodel.ViewModelFactory
import com.zacky.fundamentalsubmission.ui.dataStore
import com.zacky.fundamentalsubmission.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]
        val layoutManager = LinearLayoutManager(this)
        binding.rvListUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListUser.addItemDecoration(itemDecoration)

        val adapter = UserAdapter()
        binding.rvListUser.adapter = adapter

        mainViewModel.userProfile.observe(this) { userProfile ->
            setUserData(userProfile)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT).show()
            }
        }



        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
                    val searchItem: MenuItem = menuItem
                    val searchView = searchItem.actionView as SearchView

                    searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
                    searchView.queryHint = resources.getString(R.string.search_hint)

                    searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                        override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                            return true
                        }


                        override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                            mainViewModel.findGithubUser(LOGIN = "")
                            return true
                        }

                    })

                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            mainViewModel.findGithubUser(query.toString())
                            searchView.clearFocus()
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return false
                        }

                    })
                    return@setOnMenuItemClickListener true
                }

                R.id.favorite -> {
                    val intent = Intent(this, FavoriteUserActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }


        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun setUserData(dataUser: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(dataUser)
        binding.rvListUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}