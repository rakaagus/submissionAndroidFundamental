package com.dicoding.submissionandroidfundamental.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionandroidfundamental.adapter.ListUserAdapter
import com.dicoding.submissionandroidfundamental.data.remote.response.GithubUsers
import com.dicoding.submissionandroidfundamental.databinding.ActivityFavUserBinding
import com.dicoding.submissionandroidfundamental.ui.viewModels.FavUserViewModel
import com.dicoding.submissionandroidfundamental.ui.viewModels.ViewModelFactory

class FavUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavUserBinding
    private val favUserViewModel by viewModels<FavUserViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsersFav.layoutManager = layoutManager

        favUserViewModel.isLoading.observe(this){state ->
            showLoadingBar(state)
        }

        favUserViewModel.getUserFav().observe(this){userFav->
            val userGithub = ArrayList<GithubUsers>()
            userFav.map {
                val userGitItem = GithubUsers(login = it.login, avatarUrl = it.avatar)
                userGithub.add(userGitItem)
            }
            setUserFav(userGithub)
        }

        binding.fabBack.setOnClickListener{
            finish()
        }
    }

    private fun setUserFav(userGithub: ArrayList<GithubUsers>) {
        val adapter = ListUserAdapter()
        adapter.submitList(userGithub)
        binding.rvUsersFav.adapter = adapter

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

    private fun showLoadingBar(state: Boolean) {

    }
}