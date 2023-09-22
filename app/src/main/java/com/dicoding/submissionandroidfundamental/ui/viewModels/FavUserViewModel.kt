package com.dicoding.submissionandroidfundamental.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionandroidfundamental.data.UserGithubRepository
import com.dicoding.submissionandroidfundamental.data.local.entity.UserGithubEntity

class FavUserViewModel(private val userGithubRepository: UserGithubRepository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserFav(): LiveData<List<UserGithubEntity>> {
        _isLoading.value = false
        return userGithubRepository.getAllUserFav()
    }

    companion object{
        const val TAG = "FavUserViewModel"
    }
}