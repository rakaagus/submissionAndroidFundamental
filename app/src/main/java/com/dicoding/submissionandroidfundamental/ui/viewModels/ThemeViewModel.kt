package com.dicoding.submissionandroidfundamental.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionandroidfundamental.data.SettingsPreferences
import kotlinx.coroutines.launch

class ThemeViewModel(private val pref: SettingsPreferences): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> = pref.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkMode: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkMode)
        }
    }
}