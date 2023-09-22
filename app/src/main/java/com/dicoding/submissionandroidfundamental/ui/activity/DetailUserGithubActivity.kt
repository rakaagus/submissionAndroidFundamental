package com.dicoding.submissionandroidfundamental.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.submissionandroidfundamental.R
import com.dicoding.submissionandroidfundamental.data.remote.response.DetailUserResponse
import com.dicoding.submissionandroidfundamental.databinding.ActivityDetailUserGithubBinding
import com.dicoding.submissionandroidfundamental.adapter.SectionsPagerAdapter
import com.dicoding.submissionandroidfundamental.data.local.entity.UserGithubEntity
import com.dicoding.submissionandroidfundamental.ui.viewModels.DetailUserGithubViewModel
import com.dicoding.submissionandroidfundamental.ui.viewModels.ThemeViewModel
import com.dicoding.submissionandroidfundamental.ui.viewModels.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserGithubActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserGithubBinding
    private val detailUserGithubViewModel by viewModels<DetailUserGithubViewModel>() {
        ViewModelFactory.getInstance(application)
    }
    private val settingAppViewModel by viewModels<ThemeViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    private var username: String? = ""
    private var avatarUrl: String? = ""
    private var userGithubFav: UserGithubEntity? = null
    private var isFavUser: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window = this@DetailUserGithubActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@DetailUserGithubActivity, R.color.github_blue_dark)

        username = intent.getStringExtra(USERNAME_KEY)
        avatarUrl = intent.getStringExtra(AVATAR_KEY)

        userGithubFav.let {
            userGithubFav?.login = username.toString()
            userGithubFav?.avatar = avatarUrl.toString()
        }

        settingAppViewModel.getThemeSetting().observe(this){isDarkMode->
            if (isDarkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.fabBack.setOnClickListener(this)
        binding.fabAddFavUser.setOnClickListener(this)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        Log.d(TAG, "onCreate: $username")

        supportActionBar?.elevation = 0f
        detailUserGithubViewModel.getDetailUser(username.toString())

        detailUserGithubViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailUserGithubViewModel.detailDataUserGithub.observe(this){ data ->
            setElementInDetail(data)
        }

        detailUserGithubViewModel.isUserFav(username.toString()).observe(this){ favUser ->
            Log.d(TAG, "statusFav: $favUser")
            setUserFav(favUser)
            isFavUser = favUser
        }
    }

    private fun setUserFav(favUser: Boolean) {
        if(favUser){
            binding.fabAddFavUser.setImageResource(R.drawable.baseline_favorite_24_solid)
        }else {
            binding.fabAddFavUser.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    private fun setElementInDetail(data: DetailUserResponse) {
        Log.d(TAG, "Data: $data")
        Glide.with(this)
            .load(data.avatarUrl)
            .centerCrop()
            .into(binding.imgAvatar)
        binding.tvUsername.text = data.login
        binding.tvName.text = data.name
        if(data.bio != null){
            binding.tvBio.text = data.bio
        }else {
            binding.tvBio.text = "-"
        }

        binding.tvFollowers.text = resources.getString(R.string.coba_followers, data.followers.toString())
        binding.tvFollowings.text = resources.getString(R.string.coba_following, data.following.toString())

        if (data.company != null){
            binding?.apply {
                tvCompany.text = String.format(getString(R.string.company), data.company)
                linearRootCompany.visibility = View.VISIBLE
            }
        }else {
            binding.linearRootCompany.visibility = View.GONE
        }

        if (data.location != null){
            binding?.apply {
                tvLocation.text = String.format(getString(R.string.location), data.location)
                linearRootLocation.visibility = View.VISIBLE
            }
        }else {
            binding.linearRootLocation.visibility = View.GONE
        }

        if (data.twitterUsername != null){
            binding?.apply {
                tvTwitter.text = String.format(getString(R.string.twitter), data.twitterUsername)
                linearRootTwiter.visibility = View.VISIBLE
            }
        }else {

            binding.linearRootTwiter.visibility = View.GONE
        }

        if (data.email != null){
            binding?.apply {
                tvGmail.text = String.format(getString(R.string.gmail), data.email)
                linearRootGmail.visibility = View.VISIBLE
            }
        }else {
            binding.linearRootGmail.visibility = View.GONE
        }
    }

    private fun showLoading(show: Boolean) {
        if(show){
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding?.apply {
                progressBar.visibility = View.GONE
                imgAvatar.visibility = View.VISIBLE
                tvUsername.visibility = View.VISIBLE
                tvName.visibility = View.VISIBLE
                cvRootItem.visibility = View.VISIBLE
                constraintRootInfoUser.visibility = View.VISIBLE
                constraintRootListFollow.visibility = View.VISIBLE
            }
        }
    }

    companion object{
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val USERNAME_KEY = "username_key"
        const val AVATAR_KEY = "avatar_key"
        private const val TAG = "DetailUserGithub"
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.fab_back -> finish()
            R.id.fab_add_fav_user -> {
                if (isFavUser){
                    setUserFav(false)
                    detailUserGithubViewModel.deleteUserFav(username.toString())
                }else {
                    setUserFav(true)
                    userGithubFav = UserGithubEntity(
                        login = username.toString(),
                        avatar = avatarUrl.toString(),
                    )
                    detailUserGithubViewModel.insertUserFav(userGithubFav as UserGithubEntity)
                }
            }
        }
    }
}