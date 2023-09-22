package com.dicoding.submissionandroidfundamental.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionandroidfundamental.data.UserGithubRepository
import com.dicoding.submissionandroidfundamental.data.local.entity.UserGithubEntity
import com.dicoding.submissionandroidfundamental.data.remote.response.DetailUserResponse
import com.dicoding.submissionandroidfundamental.data.remote.response.GithubUsers
import com.dicoding.submissionandroidfundamental.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserGithubViewModel(private val mUserRepository: UserGithubRepository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailDataUserGithub = MutableLiveData<DetailUserResponse>()
    val detailDataUserGithub: LiveData<DetailUserResponse> = _detailDataUserGithub

    companion object {
        const val TAG = "DetailUserGithubViewModel"
    }

    fun insertUserFav(githubEntity: UserGithubEntity){
        viewModelScope.launch {
            mUserRepository.insertUser(githubEntity)
        }
    }

    fun deleteUserFav(username: String){
        viewModelScope.launch {
            mUserRepository.deleteFavUser(username)
        }
    }

    fun isUserFav(username: String): LiveData<Boolean> = mUserRepository.isUserFav(username)

    fun getDetailUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _detailDataUserGithub.value = response.body()
                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}