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
import com.zacky.fundamentalsubmission.databinding.ActivityMainBinding
import com.zacky.fundamentalsubmission.ui.adapter.UserAdapter
import com.zacky.fundamentalsubmission.ui.other.SettingPreferences
import com.zacky.fundamentalsubmission.ui.other.dataStore
import com.zacky.fundamentalsubmission.ui.viewmodel.MainViewModel
import com.zacky.fundamentalsubmission.ui.viewmodel.factory.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViews()
        initializeViewModel()
        setupRecyclerView()
        observeViewModel()
        setupTopAppBar()
        setupThemeSwitch()
    }

    private fun initializeViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    private fun initializeViewModel() {
        val pref = SettingPreferences.getInstance(application.dataStore)
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvListUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListUser.addItemDecoration(itemDecoration)
        binding.rvListUser.adapter = UserAdapter()
    }

    private fun observeViewModel() {
        mainViewModel.apply {
            userProfile.observe(this@MainActivity) { userProfile ->
                (binding.rvListUser.adapter as? UserAdapter)?.submitList(userProfile)
            }

            isLoading.observe(this@MainActivity) {
                showLoading(it)
            }

            snackbarText.observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let { snackBarText ->
                    Snackbar.make(window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

            getThemeSettings().observe(this@MainActivity) { isDarkModeActive: Boolean ->
                applyTheme(isDarkModeActive)
            }
        }
    }

    private fun setupTopAppBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> handleSearchMenuClick(menuItem)
                R.id.favorite -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    private fun handleSearchMenuClick(menuItem: MenuItem): Boolean {
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
                mainViewModel.findGithubUser(login = "")
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
        return true
    }

    private fun setupThemeSwitch() {
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun applyTheme(isDarkModeActive: Boolean) {
        val mode =
            if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
        findViewById<SwitchMaterial>(R.id.switch_theme).isChecked = isDarkModeActive
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
