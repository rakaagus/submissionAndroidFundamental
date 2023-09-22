package com.dicoding.submissionandroidfundamental.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.dicoding.submissionandroidfundamental.R
import com.dicoding.submissionandroidfundamental.databinding.ActivitySplashScreenBinding
import com.dicoding.submissionandroidfundamental.ui.viewModels.ThemeViewModel
import com.dicoding.submissionandroidfundamental.ui.viewModels.ViewModelFactory

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val settingAppViewModel by viewModels<ThemeViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val window: Window = this@SplashScreen.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        settingAppViewModel.getThemeSetting().observe(this){isDarkMode->
            if (isDarkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            if(isDarkMode) window.statusBarColor = ContextCompat.getColor(this@SplashScreen, R.color.black)
            else window.statusBarColor = ContextCompat.getColor(this@SplashScreen, R.color.white)
        }

        Handler().postDelayed(
            {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, DELAY_MILIS
        )
    }

    companion object{
        const val DELAY_MILIS = 1500L
    }
}