package com.dicoding.submissionandroidfundamental.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.submissionandroidfundamental.databinding.ActivitySettingAppBinding
import com.dicoding.submissionandroidfundamental.ui.viewModels.ThemeViewModel
import com.dicoding.submissionandroidfundamental.ui.viewModels.ViewModelFactory

class SettingAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingAppBinding
    private val settingAppViewModel by viewModels<ThemeViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingAppViewModel.getThemeSetting().observe(this){isDarkMode->
            if (isDarkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.switchTheme.isChecked = isDarkMode
        }
        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingAppViewModel.saveThemeSetting(isChecked)
        }

        binding.fabBack.setOnClickListener{
            finish()
        }
    }
}