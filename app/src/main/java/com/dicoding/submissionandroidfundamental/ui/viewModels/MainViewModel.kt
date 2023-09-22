package com.dicoding.submissionandroidfundamental.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionandroidfundamental.data.remote.response.GithubResponse
import com.dicoding.submissionandroidfundamental.data.remote.response.GithubUsers
import com.dicoding.submissionandroidfundamental.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isLoading

    private val _listGithubUsers = MutableLiveData<List<GithubUsers>>()
    val listGithubUsers: LiveData<List<GithubUsers>> = _listGithubUsers

    companion object{
        const val TAG = "MainViewModel"
    }

    init {
        getSearchUser()
    }

    fun getSearchUser(query: String = "raka") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUserByUsername(query)
        client.enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _listGithubUsers.value = response.body()?.items
                    }
                }else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}