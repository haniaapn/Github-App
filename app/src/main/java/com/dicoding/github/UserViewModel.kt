package com.dicoding.github

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _userGithub = MutableLiveData<GithubResponse>()
    val userGithub : LiveData<GithubResponse> = _userGithub
    private val _listUserGithub = MutableLiveData<List<UsersItem>>()
    val listUserGithub : LiveData<List<UsersItem>> = _listUserGithub
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "UserViewModel"
    }
    init {
        findUser()
    }

    private fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUser()
        client.enqueue(object : Callback<List<UsersItem>>{
            override fun onResponse(
                call: Call<List<UsersItem>>,
                response: Response<List<UsersItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val items = response.body()
                    _listUserGithub.value = items
                }else{
                    Log.e(TAG, "Data tidak ditemukan")
                }
            }

            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailur: ${t.message}")
            }
        })
    }
    fun findGithubUser(user: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(user)
        client.enqueue(object: Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>){
                _isLoading.value = false
                if (response.isSuccessful) {
                    val item = response.body()
                    _userGithub.value = item!!
                } else {
                    Log.i(TAG, "Data Not Found")
                }

            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}