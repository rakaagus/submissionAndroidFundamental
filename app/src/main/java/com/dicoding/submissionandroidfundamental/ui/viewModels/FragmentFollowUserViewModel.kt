package com.dicoding.submissionandroidfundamental.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionandroidfundamental.data.remote.response.GithubUsers
import com.dicoding.submissionandroidfundamental.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentFollowUserViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowerUser = MutableLiveData<List<GithubUsers>>()
    val listFollowerUser: LiveData<List<GithubUsers>> = _listFollowerUser

    private val _listFollowingUser = MutableLiveData<List<GithubUsers>>()
    val listFollowingUser: LiveData<List<GithubUsers>> = _listFollowingUser

    companion object{
        const val TAG = "FragmentFollowUserViewModel"
    }

    fun getFollowersUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<GithubUsers>>{
            override fun onResponse(
                call: Call<List<GithubUsers>>,
                response: Response<List<GithubUsers>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _listFollowerUser.value = response.body()
                    }
                }else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUsers>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowingUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<GithubUsers>>{
            override fun onResponse(
                call: Call<List<GithubUsers>>,
                response: Response<List<GithubUsers>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _listFollowingUser.value = response.body()
                    }
                }else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUsers>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}

