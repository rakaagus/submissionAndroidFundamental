package com.dicoding.submissionandroidfundamental.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissionandroidfundamental.data.SettingsPreferences
import com.dicoding.submissionandroidfundamental.data.UserGithubRepository
import com.dicoding.submissionandroidfundamental.data.dataStore
import com.dicoding.submissionandroidfundamental.di.Injection

class ViewModelFactory private constructor(
    private val userGithubRepository: UserGithubRepository,
    private val preferences: SettingsPreferences
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavUserViewModel::class.java)){
            return FavUserViewModel(userGithubRepository) as T
        }else if(modelClass.isAssignableFrom(DetailUserGithubViewModel::class.java)){
            return DetailUserGithubViewModel(userGithubRepository) as T
        }else if(modelClass.isAssignableFrom(ThemeViewModel::class.java)){
            return ThemeViewModel(preferences) as T

        }
        throw IllegalArgumentException("Unknow ViewModel class: " + modelClass)
    }

    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: ViewModelFactory(Injection.provideRespository(context), SettingsPreferences.getInstance(context.dataStore))
            }.also { INSTANCE = it }
    }
}