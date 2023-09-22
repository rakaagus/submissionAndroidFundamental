package com.dicoding.submissionandroidfundamental.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionandroidfundamental.R
import com.dicoding.submissionandroidfundamental.data.remote.response.GithubUsers
import com.dicoding.submissionandroidfundamental.databinding.ActivityMainBinding
import com.dicoding.submissionandroidfundamental.adapter.ListUserAdapter
import com.dicoding.submissionandroidfundamental.ui.viewModels.MainViewModel
import com.dicoding.submissionandroidfundamental.ui.viewModels.ThemeViewModel
import com.dicoding.submissionandroidfundamental.ui.viewModels.ViewModelFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val settingAppViewModel by viewModels<ThemeViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.fabUserFav.setOnClickListener(this)
        binding.fabSettingApp.setOnClickListener(this)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        mainViewModel.isloading.observe(this){
            showLoading(it)
        }

        mainViewModel.listGithubUsers.observe(this) {listUser ->
            setUserGithub(listUser)
        }
        settingAppViewModel.getThemeSetting().observe(this){isDarkMode->
            if (isDarkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.getSearchUser(searchView.text.toString())
                    false
                }
        }
    }

    private fun setUserGithub(listUser: List<GithubUsers>?) {
        val adapter = ListUserAdapter()
        adapter.submitList(listUser)
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickCallback(object: ListUserAdapter.OnClickCallback{
            override fun onItemClicked(data: GithubUsers) {
                sendUsernameToDetail(data)
            }
        })
    }

    private fun sendUsernameToDetail(data: GithubUsers) {
        val intent = Intent(this, DetailUserGithubActivity::class.java)
        intent.putExtra(DetailUserGithubActivity.USERNAME_KEY, data.login)
        intent.putExtra(DetailUserGithubActivity.AVATAR_KEY, data.avatarUrl)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.fab_user_fav -> startActivity(Intent(this, FavUserActivity::class.java))
            R.id.fab_setting_app -> startActivity(Intent(this, SettingAppActivity::class.java))
        }
    }
}